package com.tcha.guide.service;

import com.tcha.bookmark.entity.Bookmark;
import com.tcha.guide.entity.Guide;
import com.tcha.guide.mapper.GuideMapper;
import com.tcha.guide.repository.GuideRepository;
import com.tcha.utils.exceptions.business.BusinessLogicException;
import com.tcha.utils.exceptions.codes.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.tcha.guide.dto.GuideDto.Post;
import com.tcha.guide.dto.GuideDto.Patch;
import com.tcha.guide.dto.GuideDto.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class GuideService {

    private final GuideRepository guideRepository;
    private final GuideMapper guideMapper;

    //새로운 사용 가이드 생성
    public Response createGuide(Post postRequest) {
        //중복 체크 => 중복값일 경우 아래 로직으로 내려가지 않고 에러 던져짐
        duplicateVerifiedByCodeAndByTitle(postRequest.getCode(), postRequest.getTitle());

        Guide guide = guideRepository.save(guideMapper.guideDtoPostToGuide(postRequest));
        return guideMapper.guideToResponseDto(guide);
    }

    //사용 가이드 코드별 사용가이드 전체 조회
    public List<Response> findAllCodeGuide(String code) {
        List<Guide> responseGuide = findVerifiedGuideByCode(code);
        List<Response> responseList = new ArrayList<>();

        for (Guide guide : responseGuide) {
            Response response = guideMapper.guideToResponseDto(guide);
            responseList.add(response);
        }

        return responseList;
    }

    //사용 가이드 1개 조회
    public Response findOneGuide(Long id) {
        Guide guide = findVerifiedGuideById(id);

        Response response = guideMapper.guideToResponseDto(guide);
        return response;
    }

    //서비스 가이드 내용 수정
    public Response patchGuide(Long id, Patch patchGuide) {

        Guide guide = findVerifiedGuideById(id);

        guide.setCode(patchGuide.getCode());
        guide.setTitle(patchGuide.getTitle());
        guide.setContent(patchGuide.getContent());

        return guideMapper.guideToResponseDto(guide);
    }

    //존재하는 사용자 가이드인지에 대한 유효성 검증 -> id를 가지고 진행
    public Guide findVerifiedGuideById(Long guideId) {
        Guide guide = guideRepository.findById(guideId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.GUIDE_NOT_FOUND));

        return guide;
    }

    //존재하는 사용자 가이드인지에 대한 유효성 검증 -> code를 가지고 진행
    public List<Guide> findVerifiedGuideByCode(String code) {
        List<Guide> guide = guideRepository.findByGuideCode(code).orElseThrow(() -> new BusinessLogicException(ExceptionCode.GUIDE_NOT_FOUND));

        return guide;
    }

    //사용자 가이드에 대한 중복 체크 검증 -> code, title로 진행
    public void duplicateVerifiedByCodeAndByTitle(String code, String title) throws BusinessLogicException {
        guideRepository.duplicateVerifiedByCodeAndByTitle(code, title).ifPresent(guide -> {
            throw new BusinessLogicException(ExceptionCode.GUIDE_EXISTS);
        });
    }


    //사용 가이드 삭제
    public void deleteGuide(Long id) {
        guideRepository.deleteById(id);
    }


}
