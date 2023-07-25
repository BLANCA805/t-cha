package com.tcha.exercise_log.service;

import com.tcha.exercise_log.repository.ExerciseLogRepository;
import com.tcha.exercise_log.entity.ExerciseLog;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseLogService {
    private final ExerciseLogRepository exerciseLogRepository;
// TODO   private final UserService userService;


    //Pagenation으로 운동 일지를 불러옴
    @Transactional(readOnly = true)
    public Page<ExerciseLog> findExerciseLogPages(int page, int size) {

        return exerciseLogRepository.findAll(

                PageRequest.of(page-1, size, Sort.by("id").descending()));
    }
    /*
//    @Transactional(readOnly = true)
//    public Page<ExerciseLog> findExerciseLogPages(int page, int size) {
//
//        return exerciseLogRepository.findAll(
//
//                PageRequest.of(page-1, size, Sort.by("id").descending()));
//    }
    필터링에 관한 고민

     */

    //운동일지 1개 찾기
    @Transactional(readOnly = true)
    public ExerciseLog findExerciseLog(Long id) {
        Optional<ExerciseLog> exerciseLog = exerciseLogRepository.findById(id);


        return exerciseLog.get();
    }


    //운동일지 저장
    @Transactional
    public ExerciseLog createExerciseLog(ExerciseLog exerciseLog) {
//  TODO    memberService.findMember(ExerciseLog.getMember().getMemberId());   // 존재하는 유저인지 확인 (관리자인지 확인도 필요)

        return exerciseLogRepository.save(exerciseLog);
    }

    @Transactional
    public ExerciseLog updateExerciseLog(ExerciseLog exerciseLog) {
        ExerciseLog findExerciseLog = findExerciseLog(exerciseLog.getId());

        findExerciseLog.setTitle(exerciseLog.getTitle());
        findExerciseLog.setContent(exerciseLog.getContent());
//  TODO    findExerciseLog.setCreated_at();    생성일자 -> 수정일자(?) 확인 필요

        /*
        TODO
           권한 확인 하는 코드
                if (isValidAuthority(exerciseLog.getId())) { // 권한 확인 후
                    exerciseLog.setTitle(exerciseLog.getTitle());
                    exerciseLog.setExerciseLogContent(modifyExerciseLogContent(exerciseLogForm, exerciseLog));
                    return exerciseLog;
                }
         */
        return exerciseLogRepository.save(exerciseLog);

//  TODO      throw new AuthenticationServiceException("수정 권한 없음 " +  // 수정 권한 없을 시 throw
//                ":" + exerciseLog.getId());
    }
    //운동일지 삭제
    @Transactional
    public void deleteExerciseLog(Long id) {
        /*
        TODO
            if (isValidAuthority(id)) {
                exerciseLogRepository.deleteById(id);
                return;
            }
            throw new AuthenticationServiceException("삭제 권한 없음 :" + id);
        */
        ExerciseLog findExerciseLog = findExerciseLog(id);
        exerciseLogRepository.delete(findExerciseLog);
    }
    /*
     TODO
        private boolean isValidAuthority(Long exerciseLogId) {
        Long userId = this.getExerciseLog(exerciseLogId).getUser().getId();
        CustomUserDetails userDetails = UserDetailsUtil.get();

        if (userId.equals(userDetails.getId())) {
            return true;
        }

        return userDetails.hasAuthority(Authority.ADMIN);
        }
     */

}
