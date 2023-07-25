package com.tcha.bookmark.repository;

import com.tcha.bookmark.entity.BookMark;
import com.tcha.bookmark.entity.BookMark.Status;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    @Query(value = "FROM BookMark b WHERE b.trainer.id = :trainerId AND b.user.id = :userId")
    Optional<BookMark> findByTrainerIdAndUserId(UUID trainerId, String userId);


    @Query(value = "UPDATE BookMark b SET b.status = :status WHERE b.id = :id")
    int updateBookMarkStatus(Long id, Status status);


    @Query(value = "FROM BookMark b WHERE b.user.id = :userId")
    List<BookMark> findByUserId(String userId);
}
