package com.tcha.guide.repository;

import com.tcha.guide.entity.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {
    @Query(value = "FROM Guide g WHERE g.code = :code")
    List<Guide> findByGuideCode(String code);

    @Query(value = "FROM Guide g WHERE g.code = :code and g.title = :title")
    Guide findByGuideCodeAndTitle(String code, String title);

    @Modifying
    @Query(value = "UPDATE Guide g SET g.code = :code, g.title = :title, g.content = :content, g.status = :status WHERE g.id = :id")
    int updateByGuide(Long id, String code, String title, String content, Guide.Status status);

}
