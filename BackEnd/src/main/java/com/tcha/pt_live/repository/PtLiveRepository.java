package com.tcha.pt_live.repository;

import com.tcha.pt_live.entity.PtLive;
import com.tcha.user_profile.entity.UserProfile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PtLiveRepository extends JpaRepository<PtLive, Long> {

    List<PtLive> findAllByUserProfile(UserProfile userProfile);

}
