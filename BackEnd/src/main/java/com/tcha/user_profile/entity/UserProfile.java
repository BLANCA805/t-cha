package com.tcha.user_profile.entity;

import com.tcha.user.entity.User;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserProfile extends Auditable{

    @Id
    private String userProfileId;

    // 일대일 단방향 관계 설정
    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String profileImage;

}

/*
TODO

- profileImage S3 연결
 */
