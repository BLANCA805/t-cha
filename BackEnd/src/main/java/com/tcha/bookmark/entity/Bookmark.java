package com.tcha.bookmark.entity;

import com.tcha.trainer.entity.Trainer;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Bookmark extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TRAINER_ID")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "USER_PROFILE_ID")
    private UserProfile userProfile;
}
