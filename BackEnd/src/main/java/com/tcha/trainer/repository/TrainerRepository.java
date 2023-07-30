package com.tcha.trainer.repository;


import com.tcha.trainer.entity.Trainer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrainerRepository extends JpaRepository<Trainer, UUID> {

    List<Trainer> findByTagsLikeIgnoreCase(String keyword);

    @Query("SELECT t FROM Trainer t WHERE t.id = :trainer_id")
    Optional<Trainer> findById(@Param("trainer_id") UUID trainerId);

    @Query("DELETE FROM Trainer t WHERE t.id = :trainer_id")
    void deleteById(@Param("trainer_id") UUID trainerId);

}
