package com.tcha.notice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 8, nullable = false)
    private NoticeEmerStatus status;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @CreationTimestamp
    private LocalDateTime created_at;

    public enum NoticeEmerStatus {
        NORMAL("공지사항"),
        EMER("긴급 공지사항");

        @Getter
        private String status;

        NoticeEmerStatus(String status) {
            this.status = status;
        }
    }


    /*
    TODO
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        @OnDelete(action = OnDeleteAction.CASCADE)
        private User user;

     -------------------- 회원 일치 확인-------------------
        public QnaQuestion changeQnaQuestion(QnaQuestion sourceQnaQuestion, CustomBeanUtils<QnaQuestion> beanUtils) {
                // 질문을 수정하려는 회원이 질문을 등록한 회원과 일치하는가?
                this.member.checkIsMyself(sourceQnaQuestion.getMember().getMemberId());

                // 답변 완료된 질문인가?
                isQuestionAnswered();

                return beanUtils.copyNonNullProperties(sourceQnaQuestion, this);
        }

     */
}


