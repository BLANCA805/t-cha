package com.tcha.exercise_log.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tcha.pt_live.entity.PtLive;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "exerciseLog", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<ExerciseLogImage> exerciseImageList = new ArrayList<>();

    @OneToMany(mappedBy = "exerciseLog", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<ExerciseLogVideo> exerciseVideoList = new ArrayList<>();

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToOne
    @JoinColumn(name = "LIVE_ID")
    private PtLive ptLive;

}

