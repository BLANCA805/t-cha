package com.tcha.review.entity;


import com.tcha.trainer.entity.Trainer;
import com.tcha.user_profile.entity.UserProfile;
import jakarta.persistence.*;
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
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TRAINER_ID")
    private Trainer trainer;

    /*
    TODO
    @OneToOne
    @JoinColumn(name = "LIVE_ID")
    private PtLive ptLive;
     */

    @ManyToOne
    @JoinColumn(name = "USER_PROFILE_ID")
    private UserProfile userProfile;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(nullable = false)
    private float star;

    @CreationTimestamp
    private LocalDateTime created_at;

}


