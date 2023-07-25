package com.tcha.guide.service;

import com.tcha.guide.dto.GuideDto;
import com.tcha.guide.entity.Guide;
import com.tcha.guide.repository.GuideRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class GuideService {
    private final GuideRepository guideRepository;

    //새로운 사용 가이드 생성
    public Guide createGuide(Guide guide){
        return guideRepository.save(guide);
    }

    //사용 가이드 코드별 사용가이드 전체 조회
    public List<GuideDto.Response> findAllCodeGuide(String code){
        List<Guide> responseGuide = guideRepository.findByGuideCode(code);
        List<GuideDto.Response> response = new ArrayList<>();
        //dto로 변경해서 보내기!!!!

        for (Guide now: responseGuide) {
            GuideDto.Response ansGuide = GuideDto.Response.builder()
                            .id(now.getId())
                            .title(now.getTitle())
                            .code(now.getCode())
                            .content(now.getContent())
                            .status(String.valueOf(now.getStatus())).build();
            response.add(ansGuide);
        }

        return response;
    }

    //사용 가이드 1개 조회
    //
    public GuideDto.Response findOneGuide(Long id) throws Exception{
        Optional<Guide> FindGuide = guideRepository.findById(id);
        if(FindGuide.isPresent()) {
            Guide now = FindGuide.get();
            GuideDto.Response response = GuideDto.Response.builder()
                    .id(now.getId())
                    .title(now.getTitle())
                    .code(now.getCode())
                    .content(now.getContent())
                    .status(String.valueOf(now.getStatus())).build();
            return response;
        }else{
            //에러 처리가 필요함
            throw new NullPointerException("객체가 존재하지 않습니다.");
        }
    }

    //사용 가이드 삭제
//    public int deleteGuide(Long id){
//        return guideRepository.deleteById(id);
//    }

    //서비스 가이드 내용 수정
    public int patchGuide(Guide patchGuide){
        return guideRepository.updateByGuide(patchGuide.getId(), patchGuide.getCode(), patchGuide.getTitle(), patchGuide.getContent(), patchGuide.getStatus());
    }
}
