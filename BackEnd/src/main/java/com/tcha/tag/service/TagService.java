package com.tcha.tag.service;

import com.tcha.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TagService {

    private final TagRepository tagRepository;

    /*
    문자열로 이어져있는 태그 리스트를 각각의 태그로 분리
     */
    public String[] splitTagList(String tags) {
        return tags.split(",");
    }

    /*
    태그가 이미 테이블에 존재하는지 검사
     */
    public boolean checkTagDuplicate(String tag) {
        return tagRepository.existsByName(tag);
    }

    public String createTag() {
        return null;
    }

    public String updateTag() {
        return null;
    }

    public String findOneTag() {
        return null;
    }

    public String findAllTags() {
        return null;
    }

    /*
    테스트용 함수, 실제로 태그 삭제는 불가능
     */
    public boolean deleteTag() {
        return true;
    }

}
