package com.tcha.notice.service;

import com.tcha.notice.entity.Notice;
import com.tcha.notice.entity.Notice.NoticeEmerStatus;
import com.tcha.notice.mapper.NoticeMapper;
import com.tcha.notice.repository.NoticeRepository;
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
public class NoticeService {

    private final NoticeRepository noticeRepository;
// TODO   private final UserService userService;


    //Pagenation으로 공지사항을 불러옴
    @Transactional(readOnly = true)
    public Page<Notice> findNoticePages(int page, int size) {

        return noticeRepository.findAll(

                PageRequest.of(page - 1, size, Sort.by("id").descending()));
    }

    //공지사항 1개 찾기
    @Transactional(readOnly = true)
    public Notice findNotice(Long id) {
        return noticeRepository.findById(id).get();
    }

    //공지사항 저장
    @Transactional
    public Notice createNotice(Notice notice) {
//  TODO    memberService.findMember(Notice.getMember().getMemberId());   // 존재하는 유저인지 확인 (관리자인지 확인도 필요)

        return noticeRepository.save(notice);
    }


    // 공지사항 제목, 내용 수정 (생성일자?)
    @Transactional
    public Notice updateNotice(Notice notice) {
        Notice findNotice = findNotice(notice.getId());

        findNotice.setTitle(notice.getTitle());
        findNotice.setContent(notice.getContent());
//  TODO    findNotice.setCreated_at();    생성일자 -> 수정일자(?) 확인 필요

        /*
        TODO
           권한 확인 하는 코드
                if (isValidAuthority(notice.getId())) { // 권한 확인 후
                    notice.setTitle(notice.getTitle());
                    notice.setNoticeContent(modifyNoticeContent(noticeForm, notice));
                    return notice;
                }
         */
        return noticeRepository.save(notice);

//  TODO      throw new AuthenticationServiceException("수정 권한 없음 " +  // 수정 권한 없을 시 throw
//                ":" + notice.getId());
    }

    //공지사항 삭제
    @Transactional
    public void deleteNotice(Long id) {
        /*
        TODO
            if (isValidAuthority(id)) {
                noticeRepository.deleteById(id);
                return;
            }
            throw new AuthenticationServiceException("삭제 권한 없음 :" + id);
        */
        Notice findNotice = findNotice(id);
        noticeRepository.delete(findNotice);
    }
    /*
     TODO
        private boolean isValidAuthority(Long noticeId) {
        Long userId = this.getNotice(noticeId).getUser().getId();
        CustomUserDetails userDetails = UserDetailsUtil.get();

        if (userId.equals(userDetails.getId())) {
            return true;
        }

        return userDetails.hasAuthority(Authority.ADMIN);
        }
     */

}
