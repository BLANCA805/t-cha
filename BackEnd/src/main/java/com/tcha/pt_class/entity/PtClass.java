package com.tcha.pt_class.entity;

import com.tcha.trainer.entity.Trainer;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
@Table(name = "PT_CLASS")
public class PtClass extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLASS_ID")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TRAINER_ID")
    private Trainer trainer;

    private LocalDateTime startAt;

    private LocalDateTime closeAt;

    // builder chain으로 불러오는 거랑, 이렇게 함수로 불러오는 것 중에 뭐가 나을지 찾아보기
    public String getTrainerName() {
        return this.trainer.getUserProfile().getName();
    }

}
