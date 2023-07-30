package com.tcha.pt_class.controller;

import com.tcha.pt_class.dto.PtClassDto;
import com.tcha.pt_class.service.PtClassService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classes")
@Validated
@Slf4j
@RequiredArgsConstructor
public class PtClassController {

    private final PtClassService ptClassService;

    /*
    트레이너가 pt 수업 생성(오픈)
    여러 개의 수업을 한 번에 오픈 -> list로 수업 시작 시간 받아오기
     */
    @PostMapping("/{trainer-id}")
    public ResponseEntity<PtClassDto.Response> postPtClass(
            @PathVariable("trainer-id") String trainerId,
            @RequestBody List<LocalDateTime> startTimeList) {

        log.debug("[PtClassController] postPtClass 접근 확인 ::: trainerId = {}, startTimeList = {}",
                trainerId, startTimeList);

        ptClassService.createPtClass(trainerId, startTimeList); // 리턴 값(프론트에 전달할 값) 추가

        return ResponseEntity.ok().body(null);
    }

    /*
    pt 수업 정보 수정
     */
    @PatchMapping("/{trainer-id}")
    public ResponseEntity<PtClassDto.Response> patchPtClass(
            @PathVariable("trainer-id") String trainerId,
            @RequestBody PtClassDto.Patch patchRequest) {

        ptClassService.updatePtClass(trainerId, patchRequest);

        return ResponseEntity.ok().body(null);
    }

    /*
    pt 수업 id를 통해 수업 정보 조회
     */
    @GetMapping("/{pt-class-id}")
    public ResponseEntity<PtClassDto.Response> getOnePtClass(
            @PathVariable("pt-class-id") String ptClassId) {

        PtClassDto.Response ptClass = ptClassService.findOnePtClass(ptClassId);

        return ResponseEntity.ok().body(ptClass);
    }

    /*
    등록된 모든 pt 수업 조회
     */
    @GetMapping
    public ResponseEntity<List<PtClassDto.Response>> getAllPtClasses() {

        List<PtClassDto.Response> ptClassList = ptClassService.findAllPtClasses();

        return ResponseEntity.ok().body(ptClassList);
    }

    /*
    pt 수업 id를 통해 등록된 pt 수업 삭제
     */
    @DeleteMapping("{trainer-id}")
    public ResponseEntity<?> deletePtClass(
            @PathVariable("trainer-id") String trainerId,
            @RequestParam long ptClassId) {

        ptClassService.deletePtClass(trainerId, ptClassId);

        return ResponseEntity.ok().body(null);
    }
    
}
