package com.tcha.pt_live.entity;

import com.tcha.pt_class.entity.PtClass;
import com.tcha.trainer.entity.Trainer;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.utils.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PT_LIVE")
public class PtLive extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private long ptClassId;

    @ManyToOne
    private UserProfile userProfile;

    // 트레이너 아이디만
    // 트레이너 객체만
    // 트레이너의 이름이나 프로필 이미지만 따로 가지고 있을지
//    @ManyToOne
//    private Trainer trainer;

}
