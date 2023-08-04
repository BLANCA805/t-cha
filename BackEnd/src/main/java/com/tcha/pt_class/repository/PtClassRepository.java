package com.tcha.pt_class.repository;

import com.tcha.pt_class.dto.PtClassDto;
import com.tcha.pt_class.entity.PtClass;
import com.tcha.trainer.entity.Trainer;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PtClassRepository extends JpaRepository<PtClass, Long> {

    @Query("SELECT c FROM PtClass c JOIN FETCH c.trainer t WHERE t.id = :trainerId AND DATE(c.startAt) = :date")
    List<PtClass> findClassByTrainerAndDate(UUID trainerId, LocalDate date);

    List<PtClass> findClassByTrainer(String trainerId);

    @Query("SELECT c FROM PtClass c WHERE c.isDel = 0")
    List<PtClass> findAll();
}
