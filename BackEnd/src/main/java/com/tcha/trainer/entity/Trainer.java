package com.tcha.trainer.entity;

import com.tcha.user_profile.entity.UserProfile;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.UUID;
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
public class Trainer extends Auditable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "TRAINER_ID", columnDefinition = "CHAR(36)")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "USER_PROFILE_ID")
    private UserProfile userProfile;

    private String introduction;

    @Column(columnDefinition = "TEXT")
    private String tags;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

}
