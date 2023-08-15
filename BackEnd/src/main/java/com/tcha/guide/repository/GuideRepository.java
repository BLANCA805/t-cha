package com.tcha.guide.repository;

import com.tcha.guide.entity.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {
    @Query(value = "FROM Guide g WHERE g.code = :code")
    List<Guide> findByGuideCode(String code);
}
