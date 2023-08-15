package com.tcha.pt_class.repository;

import com.tcha.pt_class.entity.PtClass;
import com.tcha.trainer.entity.Trainer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PtClassRepository extends JpaRepository<PtClass, Long> {

//    @Query("SELECT c FROM PtClass c JOIN FETCH c.trainer t WHERE t.id = :trainerId AND DATE(c.startDate) = :date")
//    List<PtClass> findClassByTrainerAndDate(UUID trainerId, LocalDate date);

    @Query("SELECT c FROM PtClass c JOIN FETCH c.trainer t WHERE t.id = :trainerId AND c.isDel = 0 order by c.startDate asc, c.startTime asc")
    List<PtClass> findClassByTrainer(String trainerId);

    @Query("SELECT c FROM PtClass c WHERE c.id = :classId AND c.isDel = 0")
    Optional<PtClass> findVerifiedClassById(Long classId);

    @Query("SELECT c FROM PtClass c JOIN FETCH c.trainer t WHERE c.startDate = :date AND c.startTime BETWEEN :fromTime AND :toTime")
    List<PtClass> findClassByDate(LocalDate date, LocalTime fromTime, LocalTime toTime);

    @Query("SELECT c FROM PtClass c JOIN FETCH c.trainer t WHERE c.startDate >= :date AND c.startTime BETWEEN :fromTime AND :toTime")
    List<PtClass> findClassByTime(LocalDate date, LocalTime fromTime, LocalTime toTime);

}
