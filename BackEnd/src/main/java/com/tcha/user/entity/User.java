package com.tcha.user.entity;

import com.tcha.user_profile.entity.UserProfile;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User extends Auditable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)")
    private String id;

    // 일대일 양방향 설정 추가
    @OneToOne(mappedBy = "user")
    private UserProfile userProfile;

    //    @Column(unique = true) // email 컬럼에 unique 제약 조건 추가
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.USER_ACTIVE; //회원 가입 시, 기본값으로 "활성 계정"의 값을 지정.

    // 권한 필드 추가
    // @ElementCollection : 해당 객체가 Collection 객체임을 JPA 에게 전파
    // FetchType.LAZY : 필요할 때 가져오기, FetchType.EAGER: 바로 가져오기
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public enum UserStatus {
        USER_ACTIVE("활성 계정"),
        USER_SLEEP("휴면 계정"),
        USER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        UserStatus(String status) {
            this.status = status;
        }
    }
}

/*
TODO

- userId auto-increment 가 아닌 방법 적용하기
- Spring Security 연결하기
- FetchType 분석
- Setter, Getter 가 아닌 Builder 패턴 이용해보기
 */
