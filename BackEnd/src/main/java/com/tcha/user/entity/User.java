package com.tcha.user.entity;

import com.tcha.user_profile.entity.UserProfile;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    // 일대일 양방향 설정 추가
    @OneToOne(mappedBy = "user")
    private UserProfile userProfile;

    @Column(unique = true) // email 컬럼에 unique 제약 조건 추가
    private String email;

    // 권한 필드 추가
    // @ElementCollection : 해당 객체가 Collection 객체임을 JPA 에게 전파
    // FetchType.LAZY : 필요할 때 가져오기, FetchType.EAGER: 바로 가져오기
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

}

/*
TODO

- userId auto-increment 가 아닌 방법 적용하기
- Spring Security 연결하기
- FetchType 분석
 */
