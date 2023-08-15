package com.tcha.notice.entity;


import com.tcha.utils.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Notice extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 8, nullable = false)
    private NoticeEmerStatus status;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    public enum NoticeEmerStatus {
        NORMAL("공지사항"),
        EMER("긴급 공지사항");

        @Getter
        private String status;

        NoticeEmerStatus(String status) {
            this.status = status;
        }
    }

}


