package com.tcha.pt_live.entity;


import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.review.entity.Review;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PT_LIVE")
public class PtLive extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile userProfile;

    @NotNull
    private Long ptClassId;

    // 트레이너 아이디만?
    // 트레이너 객체만?
    // 트레이너의 이름이나 프로필 이미지만?
    @NotBlank
    @Column(columnDefinition = "CHAR(36)")
    private String trainerId;

    @OneToOne(mappedBy = "ptLive")
    private Review review;
    //상태 변경 -> 초기 생성값 progress(작성 가능)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private PtliveStatus status;

    @OneToOne(mappedBy = "ptLive")
    private ExerciseLog exerciseLogs;

    public enum PtliveStatus {
        INACCESSIBLE("접근 불가"), ACCESSIBLE("진행중"), TERMINABLE("종료가능"), TERMINATION("종료");
        @Getter
        private String status;

        PtliveStatus(String staus) {
            this.status = staus;
        }
    }

}
