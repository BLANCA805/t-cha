package com.tcha.exercise_log.entity;

import com.tcha.notice.entity.Notice;
import com.tcha.notice.entity.Notice.NoticeEmerStatus;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ExerciseLog extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;

    /*
    화상PT_라이브와 1대1 매핑
    @OneToOne
    @JoinColumn(name = "LIVE_ID")
    private PtLive ptLive;
     */

    /*
    운동일지 이미지 일대다 매핑
    @OneToMany(mappedBy = "exercise_log")
    private List<ExerciseImage> exerciseImageList = new ArrayList<>();
    */

    /*
    운동일지 동영상 일대다 매핑
    @OneToMany(mappedBy = "exercise_log")
    private List<ExerciseVideo> exerciseVideoList = new ArrayList<>();
    */
    @Column(length = 2000, nullable = false)
    private String content;


}
