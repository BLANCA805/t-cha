package com.tcha.guide.repository;

import com.tcha.guide.entity.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GuideRepository extends JpaRepository<Guide, Long> {
    @Query(value = "FROM Guide g WHERE g.code = :code")
    Optional<List<Guide>> findByGuideCode(String code);

    @Query(value = "FROM Guide g WHERE g.code = :code and g.title = :title")
    Guide findByGuideCodeAndTitle(String code, String title);

    @Modifying
    @Query(value = "UPDATE Guide g SET g.title = :title, g.content = :content WHERE g.id = :id")
    Guide updateByGuideTitleAndContent(Long id, String title, String content);

    @Modifying
    @Query(value = "UPDATE Guide g SET g.status = :status WHERE g.id = :id")
    Guide updateByGuideStauts(Long id, String status);

}
