package com.tcha.notice.controller;

import com.tcha.notice.dto.NoticeDto;
import com.tcha.notice.entity.Notice;
import com.tcha.notice.entity.Notice.NoticeEmerStatus;
import com.tcha.notice.mapper.NoticeMapper;
import com.tcha.notice.service.NoticeService;
import com.tcha.utils.pagination.MultiResponseDto;
import com.tcha.utils.pagination.PageInfo;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;


@RestController
@RequestMapping("/notices")
@Validated
@Slf4j
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final NoticeMapper noticeMapper;

    @PostMapping
    public ResponseEntity postNotice(@Valid @RequestBody NoticeDto.Post postRequest) {
        Notice noticeToService = noticeMapper.postToNotice(postRequest);
        Notice noticeForResponse = noticeService.createNotice(noticeToService);
        NoticeDto.Response response = noticeMapper.noticeToResponse(noticeForResponse);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getNoticePage(
            @RequestParam(value = "page", defaultValue = "1") @Positive Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {

        Page<Notice> noticePage = noticeService.findNoticePages(page, size);
        List<Notice> notices = noticePage.getContent();
        List<NoticeDto.Response> responses = noticeMapper.noticesToResponses(notices);

        return new ResponseEntity<>(new MultiResponseDto<>(responses, noticePage), HttpStatus.OK);
    }

    @GetMapping("/{notice-id}")
    public ResponseEntity getOneNotice(@PathVariable("notice-id") @Positive Long id) {
        Notice noticeForResponse = noticeService.findVerifiedNoticeById(id);
        NoticeDto.Response response = noticeMapper.noticeToResponse(noticeForResponse);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PatchMapping("/{notice-id}")
    public ResponseEntity patchNotice(@PathVariable("notice-id") @Positive Long id,
                                      @Valid @RequestBody NoticeDto.Patch patchRequest) {
        Notice noticeToService = noticeMapper.patchToNotice(patchRequest);
        Notice noticeForResponse = noticeService.updateNotice(noticeToService, id);
        NoticeDto.Response response = noticeMapper.noticeToResponse(noticeForResponse);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/{notice-id}")
    public ResponseEntity deleteOneNotice(@PathVariable(value = "notice-id") @Positive Long id) {
        noticeService.deleteNotice(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
