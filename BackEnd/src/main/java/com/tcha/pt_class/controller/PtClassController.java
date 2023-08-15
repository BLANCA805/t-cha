package com.tcha.pt_class.controller;

import com.tcha.pt_class.dto.PtClassDto;
import com.tcha.pt_class.service.PtClassService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    /**
     * 트레이너가 pt 수업 생성(오픈), 여러 개의 수업을 한 번에 오픈 -> list로 수업 시작 시간 받아오기
     *
     * @return 생성한 pt 수업 정보
     */
    @PostMapping()
    public ResponseEntity<List<PtClassDto.Response>> postPtClass(
            @RequestBody PtClassDto.Post postRequest) {

        List<PtClassDto.Response> classList = ptClassService.createPtClass(postRequest);
        for (PtClassDto.Response test : classList) {
            System.out.println(
                    test.getClassId() + " " + test.getTrainerId() + " " + test.getStartDate());
        }

        return new ResponseEntity<List<PtClassDto.Response>>(classList, HttpStatus.CREATED);
    }

    /**
     * 트레이너의 모든 수업 조회
     */
    @GetMapping("/{trainer-id}")
    public ResponseEntity<List<PtClassDto.Response>> getPtClassByTrainer(
            @PathVariable("trainer-id") String trainerId) {

        List<PtClassDto.Response> trainerClassList = ptClassService.findPtClassByTrainer(trainerId);

        return new ResponseEntity<List<PtClassDto.Response>>(trainerClassList, HttpStatus.OK);
    }

    /**
     * 유저가 자신의 모든 수업 조회, 추후 유저 도메인 쪽으로 이동
     */
    @GetMapping("/user/{user-profile-id}")
    public ResponseEntity<List<PtClassDto.Response>> getPtClassByUser(
            @PathVariable("user-profile-id") long userProfileId) {

        List<PtClassDto.Response> userClassList = ptClassService.findPtClassByUser(userProfileId);

        return new ResponseEntity<List<PtClassDto.Response>>(userClassList, HttpStatus.OK);
    }

    /**
     * 유저가 pt 수업 예약 or 예약 취소
     */
    @PatchMapping()
    public ResponseEntity<PtClassDto.Response> reservePtClass(
            @RequestBody PtClassDto.Patch patchRequest) {

        PtClassDto.Response ptClass = ptClassService.updatePtClass(patchRequest);

        return new ResponseEntity<PtClassDto.Response>(ptClass, HttpStatus.OK);
    }

//    /**
//     * 날짜 및 시간을 통해 수업 정보 조회
//     */
//    @GetMapping()
//    public ResponseEntity<List<PtClassDto.Response>> getPtClassByDatetime(
//            @RequestBody PtClassDto.Get getRequest) {
//
//        List<PtClassDto.Response> datetimeClassList =
//                ptClassService.findPtClassByDatetime(getRequest);
//
//        return ResponseEntity.ok().body(datetimeClassList);
//    }

    /**
     * 트레이너가 pt 수업 삭제
     */
    @DeleteMapping("/{pt-class-id}")
    public ResponseEntity<PtClassDto.Response> deletePtClass(
            @PathVariable("pt-class-id") long classId, @RequestParam String trainerId) {

        PtClassDto.Response ptClass = ptClassService.deletePtClass(classId, trainerId);

        return new ResponseEntity<PtClassDto.Response>(ptClass, HttpStatus.OK);
    }

}
