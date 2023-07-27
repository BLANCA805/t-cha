package com.tcha.notice.repository;

import com.tcha.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query(value = "select n from Notice n where n.status = :status")
    Page<Notice> findByStatus(Notice.NoticeEmerStatus status, Pageable pageable);

    @Query(value = "select n from Notice n where n.title = :title")
    Page<Notice> findByTitle(String title, Pageable pageable);

    @Query(value = "select n from Notice n where n.content = :content")
    Page<Notice> findByContent(String content, Pageable pageable);

    Notice findNoticeById(Long id);

}
