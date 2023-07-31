package com.tcha.bookmark.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tcha.trainer.entity.Trainer;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAINER_ID")
    @JsonBackReference
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile userProfile;
}
