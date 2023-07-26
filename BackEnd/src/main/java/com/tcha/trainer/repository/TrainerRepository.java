package com.tcha.trainer.repository;


import com.tcha.trainer.entity.Trainer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, UUID> {

    List<Trainer> findByTagsLikeIgnoreCase(String keyword);

    @Query(value = "SELECT * FROM trainer WHERE id = unhex(trainerId)")
    Trainer findById(@Param("trainer_id") String trainerId);

    @Query("DELETE FROM Trainer t WHERE t.id = UNHEX(:trainer_id)")
    Trainer deleteById(@Param("trainer_id") String trainerId);

}
