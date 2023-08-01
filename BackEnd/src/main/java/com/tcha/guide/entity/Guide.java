package com.tcha.guide.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String code;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.STATUS_ACTIVE;


    public enum Status {
        STATUS_ACTIVE("사용"),
        STATUS_INACTIVE("미사용");

        @Getter
        private String status;

        Status(String status) {
            this.status = status;
        }
    }

    Guide(String code, String content, String title) {
        this.code = code;
        this.content = content;
        this.title = title;
    }

}
