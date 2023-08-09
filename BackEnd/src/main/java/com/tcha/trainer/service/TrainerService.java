package com.tcha.trainer.service;

import com.tcha.pt_class.service.PtClassService;
import com.tcha.tag.entity.Tag;
import com.tcha.tag.repository.TagRepository;
import com.tcha.trainer.dto.TrainerDto;
import com.tcha.trainer.dto.TrainerDto.Rank;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.mapper.TrainerMapper;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
import io.netty.channel.unix.Unix;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TrainerService {

    private final UserProfileRepository userProfileRepository;
    private final TagRepository tagRepository;
    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;
    private final PtClassService ptClassService;

    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, Double> redisTemplateDouble;
    private final String STAR_KEY = "star";
    private final String REVIEW_KEY = "review";
    private final String PT_KEY = "PT";

    private final String BOOKMARK_KEY = "bookmark";
    private final String NEW_KEY = "new";
    private final Map<String, String> keyMap = new HashMap<>() {{
        put("평균 별점", STAR_KEY);
        put("리뷰 수", REVIEW_KEY);
        put("누적 PT 수", PT_KEY);
        put("즐겨찾기 수", BOOKMARK_KEY);
        put("최근", NEW_KEY);
    }};

    public TrainerDto.Response createTrainer(Long userProfileId, TrainerDto.Post postRequest) {

        // userProfile 객체 가져오기 (유효성 검증 로직 추가 :: 활성상태 유저인지 확인, 일반 유저인지 확인)
        UserProfile postUser = userProfileRepository.findById(userProfileId).orElseThrow();

        // 트레이너 생성
        Trainer createdTrainer = trainerRepository.save(
                trainerMapper.trainerPostDtoToTrainer(postRequest, postUser));

        // 태그 테이블 설정
        String trainerStr =
                createdTrainer.getId() + ","; // 태그 trainers에 추가할 트레이너 아이디 문자열
        String[] tagList = postRequest.getTags().split(",");
        for (String t : tagList) {
            // 이미 존재하는 태그일 경우, 트레이너 아이디 추가 & 존재하지 않는 태그일 경우 새로운 태그 생성
            if (tagRepository.findByName(t).isPresent()) { // 존재하는 태그
                Tag tag = tagRepository.findByName(t).get();
                tag.setTrainers(tag.getTrainers() + trainerStr);
            } else { // 존재하지 않는 태그
                tagRepository.save(Tag.builder().name(t).trainers(trainerStr).build());
            }
        }

        // 트레이너 이미지 테이블 설정
        ListOperations<String, Double> listOperations = redisTemplateDouble.opsForList();

        // 트레이너 레디스 등록
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        String trainerId = createdTrainer.getId();
        ZSetOperations.add(keyMap.get("평균 별점"), trainerId,
                0);
        ZSetOperations.add(keyMap.get("리뷰 수"), trainerId,
                0);
        ZSetOperations.add(keyMap.get("누적 PT 수"), trainerId,
                0);
        ZSetOperations.add(keyMap.get("즐겨찾기 수"), trainerId,
                0);
        ZSetOperations.add(keyMap.get("최근"), trainerId,
                createdTrainer.getCreatedAt().toEpochSecond(ZoneOffset.UTC));


        return trainerMapper.trainerToResponseDto(createdTrainer);
    }


    public TrainerDto.Response updateTrainer(String trainerId, TrainerDto.Patch patchRequest) {

        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow();

        trainer.setIntroduction(patchRequest.getIntroduction());
        trainer.setTitle(patchRequest.getTitle());
        trainer.setContent(patchRequest.getContent());
        trainer.setTags(patchRequest.getTags());
        // user 변경되지 않도록(set XX) 설정하기

        return trainerMapper.trainerToResponseDto(trainer);
    }

    public TrainerDto.Response findOneTrainer(String trainerId) {

        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow();

        return trainerMapper.trainerToResponseDto(trainer);
    }

    public List<TrainerDto.ResponseList> findAllTrainers() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        List<TrainerDto.ResponseList> trainerList = new ArrayList<>();
        for (Trainer t : trainerRepository.findAll()) {
            String trainerId = t.getId();

            String valueKey = "count:" + trainerId;
            String starKey = "star:" + trainerId;
            String bookmarkKey = "bookmark:" + trainerId;

            TrainerDto.ResponseList trainer = TrainerDto.ResponseList.builder()
                    .id(t.getId())
                    .introduction(t.getIntroduction())
                    .tags(t.getTags())
                    .createdAt(t.getCreatedAt())
                    .profileName(t.getUserProfile().getName())
                    .profileImg(t.getUserProfile().getProfileImage())
                    .stars(Float.parseFloat(valueOperations.get(starKey))/Float.parseFloat(valueOperations.get(valueKey)))
                    .userCount(Integer.parseInt(valueOperations.get(bookmarkKey)))
                    .ptCount(1)
                    .reviewCount(Integer.parseInt(valueOperations.get(valueKey)))
//                    .revisitGrade(0)
                    .build();

            trainerList.add(trainer);
        }

        return trainerList;
    }
    public List<TrainerDto.ResponseList> findSortedByNewTrainers() {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Set<TypedTuple<String>> typedTuples;

        //String key = "ranking";
        String key = keyMap.get("최근");

        if (ZSetOperations.size(key) >= 10) {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, 9);  //score순으로 10개 보여줌
        } else {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, ZSetOperations.size(key));
        }
        List<TrainerDto.Rank> result = typedTuples.stream().map(TrainerDto.Rank::convertToRank)
                .collect(
                        Collectors.toList());
        List<String> idList = new ArrayList<>();
        List<Float> starList = new ArrayList<>();
        List<Double> reviewCountList = new ArrayList<>();
        List<Double> PTList = new ArrayList<>();
        List<Integer> bookmarkList = new ArrayList<>();
        for (TrainerDto.Rank rank:result) {
            String trainerId = rank.getId();
            String valueKey = "count:" + trainerId;
            String bookmarkKey = "bookmark:" + trainerId;
            idList.add(trainerId);
            starList.add((float) rank.getStar());
            reviewCountList.add(Double.parseDouble(valueOperations.get(valueKey)));
            bookmarkList.add(Integer.parseInt(valueOperations.get(bookmarkKey)));
//            PTList(valueK)
        }
        List<TrainerDto.ResponseList> trainerList = new ArrayList<>();
        for (int idx=0; idx< result.size();idx++) {
            Trainer t = trainerRepository.findById(idList.get(idx)).get();
            TrainerDto.ResponseList trainer = TrainerDto.ResponseList.builder()
                    .id(t.getId())
                    .introduction(t.getIntroduction())
                    .tags(t.getTags())
                    .createdAt(t.getCreatedAt())
                    .profileName(t.getUserProfile().getName())
                    .profileImg(t.getUserProfile().getProfileImage())
                    .stars(starList.get(idx))
                    .userCount(bookmarkList.get(idx))
                    .ptCount(1)
                    .reviewCount(Double.valueOf(reviewCountList.get((idx))).intValue())
//                    .revisitGrade(0)
                    .build();

            trainerList.add(trainer);
        }

        return trainerList;
    }

    public List<TrainerDto.ResponseList> findSortedByStarTrainers() {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Set<TypedTuple<String>> typedTuples;

        //String key = "ranking";
        String key = keyMap.get("평균 별점");

        if (ZSetOperations.size(key) >= 10) {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, 9);  //score순으로 5개 보여줌
        } else {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, ZSetOperations.size(key));
        }
        List<TrainerDto.Rank> result = typedTuples.stream().map(TrainerDto.Rank::convertToRank)
                .collect(
                        Collectors.toList());

        List<String> idList = new ArrayList<>();
        List<Float> starList = new ArrayList<>();
        List<Double> reviewCountList = new ArrayList<>();
        List<Double> PTList = new ArrayList<>();
        List<Integer> bookmarkList = new ArrayList<>();
        for (TrainerDto.Rank rank:result) {
            String trainerId = rank.getId();
            String valueKey = "count:" + trainerId;
            String bookmarkKey = "bookmark:" + trainerId;
            idList.add(trainerId);
            starList.add((float) rank.getStar());
            reviewCountList.add(Double.parseDouble(valueOperations.get(valueKey)));
            bookmarkList.add(Integer.parseInt(valueOperations.get(bookmarkKey)));
//            PTList(valueK)
        }
        List<TrainerDto.ResponseList> trainerList = new ArrayList<>();
        for (int idx=0; idx< result.size();idx++) {
            Trainer t = trainerRepository.findById(idList.get(idx)).get();
            TrainerDto.ResponseList trainer = TrainerDto.ResponseList.builder()
                    .id(t.getId())
                    .introduction(t.getIntroduction())
                    .tags(t.getTags())
                    .createdAt(t.getCreatedAt())
                    .profileName(t.getUserProfile().getName())
                    .profileImg(t.getUserProfile().getProfileImage())
                    .stars(starList.get(idx))
                    .userCount(bookmarkList.get(idx))
                    .ptCount(1)
                    .reviewCount(Double.valueOf(reviewCountList.get((idx))).intValue())
//                    .revisitGrade(0)
                    .build();

            trainerList.add(trainer);
        }

        return trainerList;
    }
    public List<TrainerDto.ResponseList> findSortedByReviewTrainers() {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Set<TypedTuple<String>> typedTuples;

        //String key = "ranking";
        String key = keyMap.get("리뷰 수");

        if (ZSetOperations.size(key) >= 10) {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, 9);  //score순으로 5개 보여줌
        } else {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, ZSetOperations.size(key));
        }
        List<TrainerDto.Rank> result = typedTuples.stream().map(TrainerDto.Rank::convertToRank)
                .collect(
                        Collectors.toList());

        List<String> idList = new ArrayList<>();
        List<Float> starList = new ArrayList<>();
        List<Double> reviewCountList = new ArrayList<>();
        List<Double> PTList = new ArrayList<>();
        List<Integer> bookmarkList = new ArrayList<>();
        for (TrainerDto.Rank rank:result) {
            String trainerId = rank.getId();
            String valueKey = "count:" + trainerId;
            String bookmarkKey = "bookmark:" + trainerId;
            idList.add(trainerId);
            starList.add((float) rank.getStar());
            reviewCountList.add(Double.parseDouble(valueOperations.get(valueKey)));
            bookmarkList.add(Integer.parseInt(valueOperations.get(bookmarkKey)));
//            PTList(valueK)
        }
        List<TrainerDto.ResponseList> trainerList = new ArrayList<>();
        for (int idx=0; idx< result.size();idx++) {
            Trainer t = trainerRepository.findById(idList.get(idx)).get();
            TrainerDto.ResponseList trainer = TrainerDto.ResponseList.builder()
                    .id(t.getId())
                    .introduction(t.getIntroduction())
                    .tags(t.getTags())
                    .createdAt(t.getCreatedAt())
                    .profileName(t.getUserProfile().getName())
                    .profileImg(t.getUserProfile().getProfileImage())
                    .stars(starList.get(idx))
                    .userCount(bookmarkList.get(idx))
                    .ptCount(1)
                    .reviewCount(Double.valueOf(reviewCountList.get((idx))).intValue())
//                    .revisitGrade(0)
                    .build();

            trainerList.add(trainer);
        }

        return trainerList;
    }public List<TrainerDto.ResponseList> findSortedByPtTrainers() {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Set<TypedTuple<String>> typedTuples;

        //String key = "ranking";
        String key = keyMap.get("누적 PT 수");

        if (ZSetOperations.size(key) >= 10) {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, 9);  //score순으로 5개 보여줌
        } else {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, ZSetOperations.size(key));
        }
        List<TrainerDto.Rank> result = typedTuples.stream().map(TrainerDto.Rank::convertToRank)
                .collect(
                        Collectors.toList());

        List<String> idList = new ArrayList<>();
        List<Float> starList = new ArrayList<>();
        List<Double> reviewCountList = new ArrayList<>();
        List<Double> PTList = new ArrayList<>();
        List<Integer> bookmarkList = new ArrayList<>();
        for (TrainerDto.Rank rank:result) {
            String trainerId = rank.getId();
            String valueKey = "count:" + trainerId;
            String bookmarkKey = "bookmark:" + trainerId;
            idList.add(trainerId);
            starList.add((float) rank.getStar());
            reviewCountList.add(Double.parseDouble(valueOperations.get(valueKey)));
            bookmarkList.add(Integer.parseInt(valueOperations.get(bookmarkKey)));
//            PTList(valueK)
        }
        List<TrainerDto.ResponseList> trainerList = new ArrayList<>();
        for (int idx=0; idx< result.size();idx++) {
            Trainer t = trainerRepository.findById(idList.get(idx)).get();
            TrainerDto.ResponseList trainer = TrainerDto.ResponseList.builder()
                    .id(t.getId())
                    .introduction(t.getIntroduction())
                    .tags(t.getTags())
                    .createdAt(t.getCreatedAt())
                    .profileName(t.getUserProfile().getName())
                    .profileImg(t.getUserProfile().getProfileImage())
                    .stars(starList.get(idx))
                    .userCount(bookmarkList.get(idx))
                    .ptCount(1)
                    .reviewCount(Double.valueOf(reviewCountList.get((idx))).intValue())
//                    .revisitGrade(0)
                    .build();

            trainerList.add(trainer);
        }

        return trainerList;
    }public List<TrainerDto.ResponseList> findSortedByBookmarkTrainers() {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Set<TypedTuple<String>> typedTuples;

        //String key = "ranking";
        String key = keyMap.get("즐겨찾기 수");

        if (ZSetOperations.size(key) >= 10) {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, 9);  //score순으로 5개 보여줌
        } else {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, ZSetOperations.size(key));
        }
        List<TrainerDto.Rank> result = typedTuples.stream().map(TrainerDto.Rank::convertToRank)
                .collect(
                        Collectors.toList());

        List<String> idList = new ArrayList<>();
        List<Float> starList = new ArrayList<>();
        List<Double> reviewCountList = new ArrayList<>();
        List<Double> PTList = new ArrayList<>();
        List<Integer> bookmarkList = new ArrayList<>();
        for (TrainerDto.Rank rank:result) {
            String trainerId = rank.getId();
            String valueKey = "count:" + trainerId;
            String bookmarkKey = "bookmark:" + trainerId;
            idList.add(trainerId);
            starList.add((float) rank.getStar());
            reviewCountList.add(Double.parseDouble(valueOperations.get(valueKey)));
            bookmarkList.add(Integer.parseInt(valueOperations.get(bookmarkKey)));
//            PTList(valueK)
        }
        List<TrainerDto.ResponseList> trainerList = new ArrayList<>();
        for (int idx=0; idx< result.size();idx++) {
            Trainer t = trainerRepository.findById(idList.get(idx)).get();
            TrainerDto.ResponseList trainer = TrainerDto.ResponseList.builder()
                    .id(t.getId())
                    .introduction(t.getIntroduction())
                    .tags(t.getTags())
                    .createdAt(t.getCreatedAt())
                    .profileName(t.getUserProfile().getName())
                    .profileImg(t.getUserProfile().getProfileImage())
                    .stars(starList.get(idx))
                    .userCount(bookmarkList.get(idx))
                    .ptCount(1)
                    .reviewCount(Double.valueOf(reviewCountList.get((idx))).intValue())
//                    .revisitGrade(0)
                    .build();

            trainerList.add(trainer);
        }

        return trainerList;
    }

    public void deleteTrainer(String trainerId) {

        trainerRepository.deleteById(trainerId);
    }

    public List<TrainerDto.ResponseList> findTrainers(TrainerDto.Get search) {

        Set<Trainer> searchResult = new HashSet<>();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        // 이름, 태그로 검색
        if (search.getKeyword() != null) {
            String keyword = search.getKeyword().trim();

            // 1. 트레이너 이름으로 검색
            if (trainerRepository.findByNameContaining(keyword).isPresent()) {
                searchResult.addAll(trainerRepository.findByNameContaining(keyword).get());
            }

            // 2. 트레이너 태그로 검색
            // 2-1. 태그 검색
            List<Tag> tagList = tagRepository.findByNameContaining(keyword).orElseThrow();
            // 2-2. 태그를 가진 트레이너 조회
            for (Tag tag : tagList) {
                String[] trainers = tag.getTrainers().split(",");
                for (String trainerId : trainers) {
                    searchResult.add(
                            trainerRepository.findById(trainerId).orElseThrow());
                }
            }
        }

//        // 날짜, 시간으로 검색
//        // 선택된 날짜, 시간에 가능한 수업 조회
//        PtClassDto.Get getRequest = PtClassDto.Get.builder()
//                .date(search.getDate())
//                .fromTime(search.getFromTime())
//                .toTime(search.getToTime())
//                .build();
//
//        List<PtClassDto.Response> classList = ptClassService.findPtClassByDatetime(getRequest);
//
//        // 각 수업의 트레이너 조회
//        for (PtClassDto.Response pt_class : classList) {
//            searchResult.add(trainerRepository.findById(
//                    String(pt_class.getTrainerId())).orElseThrow());
//        }

        // 검색 결과
        List<TrainerDto.ResponseList> trainerList = new ArrayList<>();
        for (Trainer t : searchResult) {
            String trainerId = t.getId();

            String valueKey = "count:" + trainerId;
            String starKey = "star:" + trainerId;
            String bookmarkKey = "bookmark:" + trainerId;

            TrainerDto.ResponseList trainer = TrainerDto.ResponseList.builder()
                    .id(t.getId())
                    .introduction(t.getIntroduction())
                    .tags(t.getTags())
                    .createdAt(t.getCreatedAt())
                    .profileName(t.getUserProfile().getName())
                    .profileImg(t.getUserProfile().getProfileImage())
                    .stars(Float.parseFloat(valueOperations.get(starKey))/Float.parseFloat(valueOperations.get(valueKey)))
                    .userCount(Integer.parseInt(valueOperations.get(bookmarkKey)))
                    .ptCount(1)
                    .reviewCount(Integer.parseInt(valueOperations.get(valueKey)))
//                    .revisitGrade(0)
                    .build();

            trainerList.add(trainer);
        }

        return trainerList;
    }

}
