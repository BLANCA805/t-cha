package com.tcha.notice.service;


import com.tcha.notice.dto.NoticeDto;
import com.tcha.notice.entity.Notice;
import com.tcha.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
//    private final UserService userService;

    @Transactional(readOnly = true)
    public Page<Notice> findNoticePages(int page, int size) {

        return noticeRepository.findAll(
                PageRequest.of(page, size, Sort.by("id").descending()));
    }

    @Transactional(readOnly = true)
    public Notice findNotice(Long id) {
        return noticeRepository.findNoticeById(id);
    }

    @Transactional
    public Notice createNotice(Notice notice) {
//        memberService.findMember(Notice.getMember().getMemberId());   // 존재하는 유저인지 확인 (관리자인지 확인도 필요)

        return noticeRepository.save(notice);
    }

    @Transactional
    public Notice updateNotice(Notice notice) {
        Notice findNotice = findNotice(notice.getId());

//        if (isValidAuthority(notice.getId())) { // 권한 확인 후
//            notice.setTitle(notice.getTitle());
//            notice.setNoticeContent(modifyNoticeContent(noticeForm, notice));
//            return notice;
//        }
        return noticeRepository.save(notice);

//        throw new AuthenticationServiceException("수정 권한 없음 " +  // 수정 권한 없을 시 throw
//                ":" + notice.getId());
    }

    private NoticeContent modifyNoticeContent(NoticeForm noticeForm, Notice notice) {
        return noticeContentService.modifyNoticeContent(
                notice.getNoticeContent().getId(), noticeForm.getContent());
    }

    @Transactional
    public void deleteNotice(Long id) {
        if (isValidAuthority(id)) {
            noticeRepository.deleteById(id);
            return;
        }

        throw new AuthenticationServiceException("삭제 권한 없음 :" + id);
    }

//    private boolean isValidAuthority(Long noticeId) {
//        Long userId = this.getNotice(noticeId).getUser().getId();
//        CustomUserDetails userDetails = UserDetailsUtil.get();
//
//        if (userId.equals(userDetails.getId())) {
//            return true;
//        }
//
//        return userDetails.hasAuthority(Authority.ADMIN);
//    }


}
