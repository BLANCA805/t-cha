package com.tcha.review.entity;


import com.tcha.pt_live.entity.PtLive;
import com.tcha.trainer.entity.Trainer;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JoinColumnOrFormula;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Review extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TRAINER_ID")
    @NotNull
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "USER_PROFILE_ID")
    @NotNull
    private UserProfile userProfile;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private float star;

    @OneToOne
    @JoinColumn(name = "LIVE_ID")
    @NotNull
    private PtLive ptLive;

}

