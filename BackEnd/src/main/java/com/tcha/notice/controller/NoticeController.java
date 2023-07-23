package com.tcha.notice.controller;

import com.tcha.notice.dto.NoticeDto;
import com.tcha.notice.entity.Notice;
import com.tcha.notice.mapper.NoticeMapper;
import com.tcha.notice.service.NoticeService;
import com.tcha.utils.pagination.MultiResponseDto;
import com.tcha.utils.pagination.PageInfo;
import java.util.List;
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
    public ResponseEntity postNotice(@RequestBody NoticeDto.Post postRequest) {
        Notice noticeToService = noticeMapper.postToNotice(postRequest);
        Notice noticeForResponse = noticeService.createNotice(noticeToService);
        NoticeDto.Response response = noticeMapper.noticeToResponse(noticeForResponse);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping("/page")
    public ResponseEntity getNoticePage(@RequestParam(value = "page") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        Page<Notice> noticePage = noticeService.findNoticePages(page ,size);
        PageInfo pageInfo = new PageInfo(page, size, (int)noticePage.getTotalElements(),noticePage.getTotalPages());
        List<Notice> notices = noticePage.getContent();
        List<NoticeDto.Response> responses = noticeMapper.noticesToResponses(notices);

        return new ResponseEntity<>(new MultiResponseDto<>(responses, (Page)pageInfo),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneNotice(@PathVariable(value = "id") Long id) {
        Notice noticeForResponse = noticeService.findNotice(id);
        NoticeDto.Response response = noticeMapper.noticeToResponse(noticeForResponse);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity patchNotice(@PathVariable("id") Long id, @RequestBody NoticeDto.Patch patchRequest) {
        Notice noticeToService = noticeMapper.patchToNotice(patchRequest);
        Notice noticeForResponse = noticeService.updateNotice(noticeToService);
        NoticeDto.Response response = noticeMapper.noticeToResponse(noticeForResponse);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOneNotice(@PathVariable(value = "id") Long id) {
        noticeService.deleteNotice(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
