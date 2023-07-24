package com.tcha.bookmark.entity;

import com.tcha.trainer.entity.Trainer;
import com.tcha.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TRAINER_ID")
    @Column(nullable = false)
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @Column(nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column(nullable = false)
    private Status status = Status.STATUS_ACTIVE;

    public enum Status {
        STATUS_ACTIVE("등록"),
        STATUS_INACTIVE("미등록");

        @Getter
        private String status;

        Status(String status) {
            this.status = status;
        }
    }

    BookMark(String userId, Long trainerId) {
        this.trainer.setTrainerId(trainerId);
        this.user.setId(userId);
    }

}
