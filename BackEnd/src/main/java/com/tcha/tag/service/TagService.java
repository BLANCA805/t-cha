package com.tcha.tag.service;

import com.tcha.tag.entity.Tag;
import com.tcha.tag.repository.TagRepository;
import java.util.Arrays;
import java.util.List;
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
    트레이너가 해당 태그의 trainers에 포함되어 있는지 검사
     */
    public boolean checkTrainerInTag(String trainerId, String name) {
        // 태그명으로 태그 찾기
        Tag tag = tagRepository.findByName(name).orElseThrow();
        // 해당 태그의 트레이너 아이디 목록에서 파라미터 찾기
        return tag.getTrainers().contains(trainerId);
    }

    /*
    테이블에 존재하지 않던 태그 생성
     */
    public Tag createTag(String name, String trainerId) {
        return tagRepository.save(Tag.builder().name(name).trainers(trainerId + ",").build());
    }

    /*
    태그에서 트레이너 아이디 삭제 (=트레이너가 태그를 삭제한 경우)
     */
    public Tag deleteTrainerInTag(String trainerId, String name) {

        Tag tag = tagRepository.findByName(name).orElseThrow();
        tag.setTrainers(tag.getTrainers().replace(trainerId + ",", ""));

        return tag;
    }

    /*
    태그에 트레이너 아이디 추가 (=트레이너가 태그를 추가한 경우)
     */
    public Tag addTrainerInTag(String trainerId, String name) {

        Tag tag = tagRepository.findByName(name).orElseThrow();
        tag.setTrainers(tag.getTrainers() + trainerId + ",");

        return tag;
    }

    /*
    태그를 가지고 있는 트레이너 목록(배열) 조회
     */
    public String[] findOneTag(String name) {

        Tag tag = tagRepository.findByName(name).orElseThrow();

        return tag.getTrainers().split(",");
    }

    /*
    모든 태그 조회
     */
    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    /*
    테스트용 함수, 실제로 태그 삭제는 불가능
     */
    public void deleteTag(String name) {
        tagRepository.deleteByName(name);
    }

}
