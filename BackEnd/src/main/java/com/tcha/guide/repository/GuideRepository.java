package com.tcha.guide.repository;

import com.tcha.guide.entity.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {
    @Query(value = "FROM Guide g WHERE g.code = :code")
    Optional<List<Guide>> findByGuideCode(String code);


    @Query(value = "FROM Guide g WHERE g.code = :code and g.title = :title")
    Optional<List<Guide>> duplicateVerifiedByCodeAndByTitle(String code, String title);
}
