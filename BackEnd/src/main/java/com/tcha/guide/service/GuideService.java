package com.tcha.guide.service;

import com.tcha.guide.entity.Guide;
import com.tcha.guide.mapper.GuideMapper;
import com.tcha.guide.repository.GuideRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.tcha.guide.dto.GuideDto.Post;
import com.tcha.guide.dto.GuideDto.Patch;
import com.tcha.guide.dto.GuideDto.Response;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class GuideService {

    private final GuideRepository guideRepository;
    private final GuideMapper guideMapper;

    //새로운 사용 가이드 생성
    public Response createGuide(Post postRequest) {
        Guide guide = guideRepository.save(guideMapper.guideDtoPostToGuide(postRequest));
        return guideMapper.guideToResponseDto(guide);
    }

    //사용 가이드 코드별 사용가이드 전체 조회
    public List<Response> findAllCodeGuide(String code) {
        List<Guide> responseGuide = guideRepository.findByGuideCode(code);
        List<Response> responseList = new ArrayList<>();

        for (Guide guide : responseGuide) {
            Response response = guideMapper.guideToResponseDto(guide);
            responseList.add(response);
        }

        return responseList;
    }

    //사용 가이드 1개 조회
    public Response findOneGuide(Long id) {
        Guide guide = guideRepository.findById(id).get();

        Response response = guideMapper.guideToResponseDto(guide);
        return response;
    }

    //서비스 가이드 내용 수정
    public Response patchGuide(Patch patchGuide) {

        Guide guide = guideRepository.findById(patchGuide.getId()).get();

        guide.setCode(patchGuide.getCode());
        guide.setTitle(patchGuide.getTitle());
        guide.setContent(patchGuide.getContent());

        return guideMapper.guideToResponseDto(guide);
    }


    //사용 가이드 삭제
    public void deleteGuide(Long id) {
        guideRepository.deleteById(id);
    }


}
