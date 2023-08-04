package com.tcha.bookmark.repository;


import com.tcha.pt_class.entity.PtClass;
import com.tcha.trainer.entity.Trainer;
import com.tcha.user_profile.entity.UserProfile;

import com.tcha.bookmark.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    @Query(value = "FROM Bookmark b WHERE b.userProfile = :userProFile")
    Page<Bookmark> findByUserProfile(UserProfile userProFile, Pageable pageable);

    @Query(value = "FROM Bookmark b WHERE b.userProfile = :userProfile AND b.trainer = :trainer")
    Bookmark findBookmarkByUserProfileAndTrainer(UserProfile userProfile, Trainer trainer);
}
