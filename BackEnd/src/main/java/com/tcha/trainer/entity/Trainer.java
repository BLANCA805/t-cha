package com.tcha.trainer.entity;

import com.tcha.bookmark.entity.Bookmark;
import com.tcha.review.entity.Review;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
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
//    @Column(name = "TRAINER_ID", columnDefinition = "BINARY(16)")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToOne
    private UserProfile userProfile;

    private String introduction;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String tags;

    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    /*
    ----------- 다대일 양방향을 위한 필드 -----------
     */

    // 북마크
    @OneToMany(mappedBy = "trainer")
    private List<Bookmark> bookmarks = new ArrayList<>();

    // 리뷰
    @OneToMany(mappedBy = "trainer")
    private List<Review> reviews = new ArrayList<>();

//    // 클래스
//    @OneToMany(mappedBy = "trainer")
//    private List<PtClass> classes = new ArrayList<>();
//
//    // 트레이너 이미지
//    @OneToMany(mappedBy = "trainer")
//    private List<TrainerImage> images = new ArrayList<>();

}
