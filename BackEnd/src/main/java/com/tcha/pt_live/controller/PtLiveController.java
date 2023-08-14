package com.tcha.pt_live.controller;

//import com.tcha.pt_live.dto.PtLiveDto;

import com.tcha.pt_live.dto.PtLiveDto;
import com.tcha.pt_live.service.PtLiveService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lives")
@Validated
@Slf4j
@RequiredArgsConstructor
public class PtLiveController {

    private final PtLiveService ptLiveService;

//    /*
//    유저가 PT 수업 예약
//    결제 성공 시 live room 생성
//     */
//    @PostMapping()
//    public ResponseEntity<PtLiveDto.Response> postPtLive(
//            @RequestBody PtLiveDto.Post postRequest) {
//
//        PtLiveDto.Response createdLive = ptLiveService.createPtLive(postRequest);
//
//        return ResponseEntity.ok().body(createdLive);
//    }
//
//    /*
//    live 정보 수정
//    - 언제?
//        - 유저가 예약 취소했을 경우
//        - 연결된 userProfile이 null로 변경됨
//     */
//    @PatchMapping()
//    public ResponseEntity<PtLiveDto.Response> patchPtLive(
//            @RequestBody PtLiveDto.Patch patchRequest) {
//
//        ptLiveService.updatePtLive(ptLiveId, userProfileId);
//
//        return ResponseEntity.ok().body(null);
//    }

    /*
    pt live id를 통해 라이브 정보 조회
     */
    @GetMapping("/{pt-live-id}")
    public ResponseEntity<PtLiveDto.Response> getOnePtLive(
            @PathVariable("pt-live-id") long ptLiveId) {

        PtLiveDto.Response ptLive = ptLiveService.findOnePtLive(ptLiveId);

        return ResponseEntity.ok().body(ptLive);
    }

//    /*
//    pt live id를 통해 등록된 pt 라이브 삭제
//     */
//    @DeleteMapping("{pt-live-id}")
//    public ResponseEntity<?> deletePtLive(@PathVariable("pt-live-id") long ptLiveId) {
//
//        ptLiveService.deletePtLive(ptLiveId);
//
//        return ResponseEntity.ok().body(null);
//    }

}
