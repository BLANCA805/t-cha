package com.tcha.review.entity;


import com.tcha.trainer.entity.Trainer;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.utils.audit.Auditable;
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
public class Review extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TRAINER_ID")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "USER_PROFILE_ID")
    private UserProfile userProfile;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(nullable = false)
    private float star;

    /*
    TODO
    @OneToOne
    @JoinColumn(name = "LIVE_ID")
    private PtLive ptLive;
     */
}

//    @OneToOne
//    @JoinTable(name = "TRAINER_PROFILE", //조인테이블명
//            joinColumns = @JoinColumn(name="TRAINER_ID"),  //외래키
//            inverseJoinColumns = @JoinColumn(name="USER_PROFILE_ID") //반대 엔티티의 외래키
//    )
//    private UserProfile trainerProfile;
