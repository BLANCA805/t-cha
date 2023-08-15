package com.tcha.user_profile.entity;

import com.tcha.answer.entity.Answer;
import com.tcha.bookmark.entity.Bookmark;
import com.tcha.pt_live.entity.PtLive;
import com.tcha.question.entity.Question;
import com.tcha.review.entity.Review;
import com.tcha.trainer.entity.Trainer;
import com.tcha.user.entity.User;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserProfile extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "USER_PROFILE_ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne(mappedBy = "userProfile")
    private Trainer trainer;

    @OneToMany(mappedBy = "userProfile")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "userProfile")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "userProfile")
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "userProfile")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "userProfile")
    private List<PtLive> ptLives = new ArrayList<>();

    //    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String profileImage;

}

/*
TODO

- profileImage S3 연결
 */
