package com.tcha.pt_class.repository;

import com.tcha.pt_class.entity.PtClass;
import com.tcha.trainer.entity.Trainer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PtClassRepository extends JpaRepository<PtClass, Long> {

    @Query("SELECT c FROM PtClass c JOIN FETCH c.trainer t WHERE t.id = :trainerId AND DATE(c.startDate) = :date")
    List<PtClass> findClassByTrainerAndDate(UUID trainerId, LocalDate date);

    @Query("SELECT c FROM PtClass c JOIN FETCH c.trainer t WHERE t.id = :trainerId AND c.isDel = 0")
    List<PtClass> findClassByTrainer(UUID trainerId);

    @Query("SELECT c FROM PtClass c WHERE c.id = :classId AND c.isDel = 0")
    Optional<PtClass> findVerifiedClassById(Long classId);

//    @Query("SELECT c FROM PtClass c JOIN FETCH c.trainer t WHERE c.startAt BETWEEN :fromDate AND :toDate")
//    List<PtClass> findClassByDate(LocalDateTime fromDate, LocalDateTime toDate);
//
//    @Query("SELECT c FROM PtClass c JOIN FETCH c.trainer t WHERE c.startAt >= :fromTime AND TIME(c.startAt) <= :toTime")
//    List<PtClass> findClassByTime(LocalDateTime fromTime, LocalDateTime toTime);

//    @Query("SELECT c FROM PtClass c WHERE c.isDel = 0")
//    List<PtClass> findAllClass();
}
