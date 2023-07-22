package com.tcha.notice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.Map;


@RestController
@RequestMapping("/notices")
@Validated
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;

    public String getNoticePage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            Model model) {

        Page<Notice> noticePage = noticeService.getNoticePage(page - 1);
        model.addAttribute("noticePage", noticePage);

        Map<String, Integer> pageNav = PageNavigationUtil.getNavMap(noticePage, PageSize.NOTICE);
        model.addAttribute("pageNav", pageNav);

        return "notices";
    }

    @GetMapping("/notices/{id}")
    public String getNotice(@PathVariable(value = "id") Long id, Model model) {
        Notice notice = noticeService.getNotice(id);
        model.addAttribute("notice", notice);
        return "content";
    }

    @PostMapping("/notices")
    public String addNotice(@Valid @ModelAttribute NoticeForm noticeForm) {
        noticeService.addNotice(noticeForm);
        return "redirect:/notices";
    }

    @DeleteMapping("/notices/{id}")
    public String deleteNotice(@PathVariable(value = "id") Long id) {
        noticeService.deleteNotice(id);
        return "redirect:/notices";
    }

    @PutMapping("/notices")
    public String modifyNotice(@Valid @ModelAttribute NoticeForm noticeForm) {
        noticeService.modifyNotice(noticeForm);
        return "redirect:/notices";
    }

    @GetMapping("/edit")
    public String getEditForm() {
        return "edit";
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable(value = "id") Long id, Model model) {
        Notice notice = noticeService.getNotice(id);
        model.addAttribute("notice", notice);
        return "edit";
    }
}
