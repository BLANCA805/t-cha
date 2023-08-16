package com.tcha.pt_live.repository;

import com.tcha.pt_live.entity.PtLive;

import java.util.List;
import java.util.Optional;

import com.tcha.user_profile.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PtLiveRepository extends JpaRepository<PtLive, Long> {

    List<PtLive> findAllByUserProfile(UserProfile userProfile);

    //findAllByStatusInaccessible
    @Query("SELECT p FROM PtLive p WHERE p.status = 'INACCESSIBLE'")
    Optional<List<PtLive>> findAllByStatusInaccessible();

    //진행
    @Query("SELECT p FROM PtLive p WHERE p.status = 'ACCESSIBLE'")
    Optional<List<PtLive>> findAllByStatusAccessible();

    @Query("SELECT p FROM PtLive p WHERE p.status = 'TERMINABLE'")
    Optional<List<PtLive>> findAllByStatusTerminable();
}
