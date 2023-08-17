package com.tcha.exercise_log.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tcha.guide.entity.Guide;
import com.tcha.pt_live.entity.PtLive;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ElementCollection
    @OrderColumn
    private List<String> images = new ArrayList<>();

    @ElementCollection
    @OrderColumn
    private List<String> videos = new ArrayList<>();

    @ElementCollection
    @OrderColumn
    private List<String> contents = new ArrayList<>();

//    @Column(columnDefinition = "TEXT", nullable = false)
//    private String content;

    @OneToOne
    @JoinColumn(name = "LIVE_ID")
    @NotNull
    private PtLive ptLive;

    //상태 변경 -> 초기 생성값 write(작성 가능)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private exerciseLogStatus status;
//    = exerciseLogStaus.WRITE;

    public enum exerciseLogStatus {
        READ("읽기"), WRITE("쓰기");

        @Getter
        private String status;

        exerciseLogStatus(String staus) {
            this.status = staus;
        }
    }

}

