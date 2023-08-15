package com.tcha.question.entity;

import com.tcha.answer.entity.Answer;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class Question extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "QUESTION_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_PROFILE_ID")
    private UserProfile userProfile;

    @Column(nullable = true)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String content;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private QuestionStatus status;

    public enum QuestionStatus {
        QUESTION_PUBLIC("공개"),
        QUESTION_PRIVATE("비공개");

        @Getter
        private String status;

        QuestionStatus(String status) {
            this.status = status;
        }
    }
}
