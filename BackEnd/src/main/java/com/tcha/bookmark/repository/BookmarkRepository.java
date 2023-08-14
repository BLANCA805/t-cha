package com.tcha.bookmark.repository;

import com.tcha.trainer.entity.Trainer;
import com.tcha.user_profile.entity.UserProfile;

import java.util.List;
import java.util.Optional;

import com.tcha.bookmark.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query(value = "FROM Bookmark b WHERE b.userProfile = :userProFile")
    Page<Bookmark> findByUserProfile(UserProfile userProFile, Pageable pageable);

    @Query(value = "FROM Bookmark b WHERE b.userProfile.id = :userProfileId AND b.trainer.id = :trainerId")
    Optional<Bookmark> findBookmarkByUserProfileAndTrainer(Long userProfileId,
                                                           String trainerId);

    @Query(value = "FROM Bookmark b WHERE b.trainer.id = :trainerId")
    List<Bookmark> findByTrainerId(String trainerId);
}
