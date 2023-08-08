package com.tcha.trainer.repository;


import com.tcha.trainer.entity.Trainer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrainerRepository extends JpaRepository<Trainer, UUID> {

    @Query("SELECT t FROM Trainer t WHERE t.userProfile.name LIKE %:keyword%")
    Optional<List<Trainer>> findByNameContaining(String keyword);

    @Query("SELECT t FROM Trainer t WHERE t.id IN :idList")
    Optional<List<Trainer>> findByIdList(@Param("idList") List<UUID> idList);



}
