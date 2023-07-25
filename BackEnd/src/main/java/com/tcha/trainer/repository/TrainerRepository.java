package com.tcha.trainer.repository;


import com.tcha.trainer.entity.Trainer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, UUID> {

}
