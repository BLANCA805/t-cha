package com.tcha.pt_live.controller;

import com.tcha.pt_live.dto.PtLiveDto;
import com.tcha.pt_live.service.PtLiveService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/lives")
@Validated
@Slf4j
@RequiredArgsConstructor
public class PtLiveController {

//    @Value("${OPENVIDU_URL}")
//    private String OPENVIDU_URL;
//
//    @Value("${OPENVIDU_SECRET}")
//    private String OPENVIDU_SECRET;
//
//    private OpenVidu openvidu;
//
//    @PostConstruct
//    public void init() {
//        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
//    }

    private final PtLiveService ptLiveService;

    /*
    유저가 PT 수업 예약
    결제 성공 시 live room 생성
    -> 오픈비두 session 초기화(initializeSession)ㅈ
     */
//    @PostMapping("/{class-id}/{user-profile-id}")
//    public ResponseEntity<PtLiveDto.Response> postPtLive(
//            @PathVariable("class-id") long ptClassId,
//            @PathVariable("user-profile-id") long userProfileId)
//            throws OpenViduJavaClientException, OpenViduHttpException {
//
//        log.debug("[PtLiveController] postPtLive 접근 확인 ::: ptClassId = {}, userProfileId = {}",
//                ptClassId, userProfileId);
//
//        // 결제 로직 추가
//
//        ptLiveService.createPtLive(ptClassId, userProfileId); // 리턴 값(프론트에 전달할 값) 추가
//
//        return ResponseEntity.ok().body(null);
//    }

//    @PostMapping("/api/sessions")
//    public ResponseEntity<String> initializeSession(
//            @RequestBody(required = false) Map<String, Object> params)
//            throws OpenViduJavaClientException, OpenViduHttpException {
//        SessionProperties properties = SessionProperties.fromJson(params).build();
//        Session session = openvidu.createSession(properties);
//        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
//    }

    /*
    live 정보 수정
    - 언제?
        - 유저 또는 트레이너가 예약 취소했을 경우
        - 연결된 userProfile이 null로 변경됨
     */
    @PatchMapping("/{pt-live-id}/{user-profile-id}")
    public ResponseEntity<PtLiveDto.Response> patchPtLive(
            @PathVariable("pt-live-id") long ptLiveId,
            @PathVariable("user-profile-id") long userProfileId) {

        ptLiveService.updatePtLive(ptLiveId, userProfileId);

        return ResponseEntity.ok().body(null);
    }

    /*
    pt live id를 통해 라이브 정보 조회
     */
    @GetMapping("/{pt-live-id}")
    public ResponseEntity<PtLiveDto.Response> getOnePtLive(
            @PathVariable("pt-live-id") long ptLiveId) {

        PtLiveDto.Response ptLive = ptLiveService.findOnePtLive(ptLiveId);

        return ResponseEntity.ok().body(ptLive);
    }

    /*
    pt live id를 통해 등록된 pt 라이브 삭제
     */
    @DeleteMapping("{pt-live-id}")
    public ResponseEntity<?> deletePtLive(@PathVariable("pt-live-id") long ptLiveId) {

        ptLiveService.deletePtLive(ptLiveId);

        return ResponseEntity.ok().body(null);
    }

}
