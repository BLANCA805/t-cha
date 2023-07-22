package com.tcha.guide.service;

import com.tcha.guide.entity.Guide;
import com.tcha.guide.mapper.GuideMapper;
import com.tcha.guide.repository.GuideRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class GuideService {
    private GuideRepository guideRepository;
//    private GuideMapper guideMapper;

    //새로운 사용 가이드 생성
    public Guide createGuide(Guide guide){
        return guideRepository.save(guide);
    }

    //사용 가이드 코드별 사용가이드 전체 조회
    public Optional<List<Guide>> findGuides(String code){
        return guideRepository.findByGuideCode(code);
    }

    //사용 가이드 1개 조회
    public Optional<Guide> findGuide(Long id){
        return guideRepository.findById(id);
    }

    //사용 가이드 삭제
//    public int deleteGuide(Long id){
//        return guideRepository.deleteById(id);
//    }

    //서비스 가이드 내용 수정
    public Guide patchGuide(Long id, String title, String content){
        return guideRepository.updateByGuideTitleAndContent(id, title, content);
    }

    //서비스 가이드 등록 해제
    public Guide patchDeregistGuide(Long id, String status){
        return guideRepository.updateByGuideStauts(id, status);
    }
}
