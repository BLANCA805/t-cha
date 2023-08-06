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
    반환 값 -> 해당 트레이너의 전체 수업 일정
     */
    @PostMapping()
    public ResponseEntity<List<PtClassDto.Response>> postPtClass(
            @RequestBody PtClassDto.Post postRequest) {

        List<PtClassDto.Response> classList = ptClassService.createPtClass(postRequest);

        return ResponseEntity.ok().body(classList);
    }

    /*
    pt 수업 정보 수정(== 삭제)
     */
    @PatchMapping()
    public ResponseEntity<List<PtClassDto.Response>> patchPtClass(
            @RequestBody PtClassDto.Patch patchRequest) {

        List<PtClassDto.Response> classList = ptClassService.updatePtClass(patchRequest);

        return ResponseEntity.ok().body(classList);
    }

    /*
    트레이너 id를 통해 해당 트레이너의 수업 조회
     */
    @GetMapping("/{trainer-id}")
    public ResponseEntity<List<PtClassDto.Response>> getPtClassByTrainer(
            @PathVariable("trainer-id") String trainerId) {

        List<PtClassDto.Response> trainerClassList = ptClassService.findPtClassByTrainer(trainerId);

        return ResponseEntity.ok().body(trainerClassList);
    }

    /*
    날짜 및 시간을 통해 수업 정보 조회
     */
    @GetMapping()
    public ResponseEntity<List<PtClassDto.Response>> getPtClassByDatetime(
            @RequestBody PtClassDto.Get getRequest) {

        List<PtClassDto.Response> datetimeClassList =
                ptClassService.findPtClassByDatetime(getRequest);

        return ResponseEntity.ok().body(datetimeClassList);
    }

    /*
    등록된 모든 pt 수업 조회
     */
//    @GetMapping
//    public ResponseEntity<List<PtClassDto.Response>> getAllPtClasses() {
//
//        List<PtClassDto.Response> ptClassList = ptClassService.findAllPtClasses();
//
//        return ResponseEntity.ok().body(ptClassList);
//    }

    /*
    pt 수업 id를 통해 등록된 pt 수업 삭제
     */
//    @DeleteMapping("{trainer-id}")
//    public ResponseEntity<?> deletePtClass(
//            @PathVariable("trainer-id") String trainerId,
//            @RequestParam long ptClassId) {
//
//        ptClassService.deletePtClass(trainerId, ptClassId);
//
//        return ResponseEntity.ok().body(null);
//    }

}
