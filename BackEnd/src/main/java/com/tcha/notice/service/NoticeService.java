package com.tcha.notice.service;

import com.tcha.guide.entity.Guide;
import com.tcha.notice.entity.Notice;
import com.tcha.notice.entity.Notice.NoticeEmerStatus;
import com.tcha.notice.mapper.NoticeMapper;
import com.tcha.notice.repository.NoticeRepository;
import com.tcha.utils.exceptions.business.BusinessLogicException;
import com.tcha.utils.exceptions.codes.ExceptionCode;
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

    //공지사항 저장
    @Transactional
    public Notice createNotice(Notice notice) {
        return noticeRepository.save(notice);
    }


    // 공지사항 제목, 내용 수정 (생성일자?)
    @Transactional
    public Notice updateNotice(Notice notice, long id) {
        Notice findNotice = findVerifiedNoticeById(id);

        findNotice.setTitle(notice.getTitle());
        findNotice.setContent(notice.getContent());

        return noticeRepository.save(findNotice);

    }

    //공지사항 삭제
    @Transactional
    public void deleteNotice(Long id) {
        Notice findNotice = findVerifiedNoticeById(id);
        noticeRepository.delete(findNotice);
    }

    //존재하는 공지사항인지에 대한 유효성 검증 -> id로 진행
    @Transactional(readOnly = true)
    public Notice findVerifiedNoticeById(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOTICE_NOT_FOUND));

        return notice;
    }

//    //공지사항 1개 찾기
//    @Transactional(readOnly = true)
//    public Notice findNotice(Long id) {
//        return findVerifiedNoticeById(id);
//    }
}
