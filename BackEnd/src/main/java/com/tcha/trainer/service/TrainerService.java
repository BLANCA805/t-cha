package com.tcha.trainer.service;


import com.tcha.bookmark.entity.Bookmark;
import com.tcha.bookmark.repository.BookmarkRepository;
import com.tcha.bookmark.service.BookmarkService;
import com.tcha.pt_class.dto.PtClassDto;
import com.tcha.pt_class.service.PtClassService;
import com.tcha.question.entity.Question;
import com.tcha.tag.entity.Tag;
import com.tcha.tag.repository.TagRepository;
import com.tcha.trainer.dto.TrainerDto;
import com.tcha.trainer.dto.TrainerDto.Rank;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.mapper.TrainerMapper;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.user.entity.User;
import com.tcha.user.service.UserService;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
import com.tcha.user_profile.service.UserProfileService;
import com.tcha.utils.exceptions.business.BusinessLogicException;
import com.tcha.utils.exceptions.codes.ExceptionCode;
import com.tcha.utils.pagination.PageInfo;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TrainerService {

    //    private final UserProfileRepository userProfileRepository;
    private final TagRepository tagRepository;
    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;
    private final PtClassService ptClassService;
    private final UserService userService;
    private final UserProfileService userProfileService;
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkService bookmarkService;

    private final RedisTemplate<String, String> redisTemplate;
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

    public Trainer createTrainer(Trainer trainer) {

        // userProfile 객체 가져오기 (유효성 검증 로직 추가 :: 활성상태 유저인지 확인, 일반 유저인지 확인 & 이미 트레이너 권한을 갖고 있는지 확인)
        UserProfile postUser = userProfileService.findVerifiedUserProfile(
                trainer.getUserProfile().getId());
        User user = userService.findVerifiedUser(postUser.getUser().getId());

        // 해당 유저에게 트레이너 권한 제공
        List<String> userRoles = user.getRoles();
        if (userRoles.contains("TRAINER")) {
            throw new BusinessLogicException(ExceptionCode.TRAINER_EXISTS);
        } else {
            userRoles.add("TRAINER");
            userService.createUser(user);
        }

        // 트레이너 생성
        Trainer createdTrainer = trainerRepository.save(trainer);

        // 태그 테이블 설정
        String trainerStr =
                createdTrainer.getId() + ","; // 태그 trainers에 추가할 트레이너 아이디 문자열
        String[] tagList = trainer.getTags().split(",");
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
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        // 트레이너 레디스 등록
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        String trainerId = createdTrainer.getId();

        String starKey = "star:" + trainerId;
        String reviewCountKey = "reviewCount:" + trainerId;
        String ptCountKey = "ptCount:" + trainerId;
        String bookmarkCountKey = "bookmarkCount:" + trainerId;
        String newKey = "new:" + trainerId;
        valueOperations.set(starKey, "0");
        valueOperations.set(reviewCountKey, "0");
        valueOperations.set(ptCountKey, "0");
        valueOperations.set(bookmarkCountKey, "0");
        if (valueOperations.get("trainerCount") == null) {
            valueOperations.set("trainerCount", "1");
        } else {
            valueOperations.set("trainerCount", String.valueOf(Integer.parseInt(valueOperations.get("trainerCount")) + 1));
        }


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

        return createdTrainer;
    }

    public Trainer updateTrainer(String trainerId, Trainer trainer) {

        // 트레이너 유효성 검증 로직 추가
        Trainer trainerForSave = findVerifiedTrainer(trainerId);
        // 태그 테이블 설정
        String trainerStr =
                trainerForSave.getId() + ","; // 태그 trainers에 추가할 트레이너 아이디 문자열
        String[] tagList = trainer.getTags().split(",");
        for (String t : tagList) {
            // 이미 존재하는 태그일 경우, 트레이너 아이디 추가 & 존재하지 않는 태그일 경우 새로운 태그 생성
            if (tagRepository.findByName(t).isPresent()) { // 존재하는 태그
                Tag tag = tagRepository.findByName(t).get();
                tag.setTrainers(tag.getTrainers() + trainerStr);
            } else { // 존재하지 않는 태그
                tagRepository.save(Tag.builder().name(t).trainers(trainerStr).build());
            }
        }
        Optional.ofNullable(trainer.getIntroduction())
                .ifPresent(introduction -> trainerForSave.setIntroduction(introduction));
        Optional.ofNullable(trainer.getTitle())
                .ifPresent(title -> trainerForSave.setTitle(title));
        Optional.ofNullable(trainer.getContent())
                .ifPresent(content -> trainerForSave.setContent(content));
        Optional.ofNullable(trainer.getTags())
                .ifPresent(tags -> trainerForSave.setTags(tags));
        Optional.ofNullable(trainer.getImages())
                .ifPresent(images -> trainerForSave.setImages(images));

        // 연결된 user 변경되지 않도록(setUserProfile 안되도록) 설정하기

        return trainerRepository.save(trainerForSave);
    }

    public TrainerDto.Response findOneTrainer(String trainerId) {

        // 트레이너 유효성 검증 추가
        Trainer trainer = findVerifiedTrainer(trainerId);
        List<Long> userProfileIdList = findUserProfileIdByBookmark(trainer.getId());

        return trainerMapper.trainerToResponseDto(trainer, userProfileIdList);
    }

    public Page<Trainer> findAllTrainers(int page, int size) {

        Page<Trainer> pageTrainers = trainerRepository.findAll(
                PageRequest.of(page, size));

        return pageTrainers;

    }


    public List<TrainerDto.ResponseList> findSortedByNewTrainers(int page, int size) {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Set<TypedTuple<String>> typedTuples;

        //String key = "ranking";
        String key = keyMap.get("최근");

        if (ZSetOperations.size(key) >= page * 10 + size) {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, page * 10, page * 10 + 9);  //score순으로 10개 보여줌
        } else {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, page * 10, ZSetOperations.size(key));
        }
        List<TrainerDto.Rank> result = typedTuples.stream().map(TrainerDto.Rank::convertToRank)
                .collect(
                        Collectors.toList());
        System.out.println(result);
        List<String> idList = new ArrayList<>();
        List<Float> starList = new ArrayList<>();
        List<Double> reviewCountList = new ArrayList<>();
        List<Double> PTList = new ArrayList<>();
        List<Double> bookmarkList = new ArrayList<>();
        for (TrainerDto.Rank rank : result) {
            String trainerId = rank.getId();
            String reviewCountKey = "reviewCount:" + trainerId;
            String bookmarkCountKey = "bookmarkCount:" + trainerId;
            String ptCountKey = "ptCount:" + trainerId;
            String starKey = "star:" + trainerId;
            idList.add(trainerId);
            if (valueOperations.get(reviewCountKey).equals("0")) {
                starList.add(0f);
            } else {
                starList.add(Float.parseFloat(valueOperations.get(starKey)) / Float.parseFloat(valueOperations.get(reviewCountKey)));

            }
            reviewCountList.add(Double.parseDouble(valueOperations.get(reviewCountKey)));
            bookmarkList.add(Double.parseDouble(valueOperations.get(bookmarkCountKey)));
            PTList.add(Double.parseDouble(valueOperations.get(ptCountKey)));
        }
        List<TrainerDto.ResponseList> trainerList = new ArrayList<>();
        for (int idx = 0; idx < result.size(); idx++) {
            Trainer t = trainerRepository.findById(idList.get(idx)).get();
            List<Long> userProfileIdList = findUserProfileIdByBookmark(t.getId());

            TrainerDto.ResponseList trainer = TrainerDto.ResponseList.builder()
                    .id(t.getId())
                    .introduction(t.getIntroduction())
                    .tags(t.getTags())
                    .createdAt(t.getCreatedAt().toLocalDate())
                    .profileName(t.getUserProfile().getName())
                    .profileImg(t.getUserProfile().getProfileImage())
                    .stars(starList.get(idx))
                    .bookmarkCount(Double.valueOf(bookmarkList.get(idx)).intValue())
                    .ptCount(Double.valueOf(PTList.get(idx)).intValue())
                    .reviewCount(Double.valueOf(reviewCountList.get((idx))).intValue())
                    .userProfileIdList(userProfileIdList)
                    .build();

            trainerList.add(trainer);
        }

        return trainerList;
    }

    public List<TrainerDto.ResponseList> findSortedByStarTrainers(int page, int size) {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Set<TypedTuple<String>> typedTuples;

        //String key = "ranking";
        String key = keyMap.get("평균 별점");

        if (ZSetOperations.size(key) >= page * 10 + size) {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, page * 10, page * 10 + 9);  //score순으로 10개 보여줌
        } else {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, page * 10, ZSetOperations.size(key));
        }
        List<TrainerDto.Rank> result = typedTuples.stream().map(TrainerDto.Rank::convertToRank)
                .collect(
                        Collectors.toList());

        List<String> idList = new ArrayList<>();
        List<Float> starList = new ArrayList<>();
        List<Double> reviewCountList = new ArrayList<>();
        List<Double> PTList = new ArrayList<>();
        List<Double> bookmarkList = new ArrayList<>();
        for (TrainerDto.Rank rank : result) {
            String trainerId = rank.getId();
            String reviewCountKey = "reviewCount:" + trainerId;
            String bookmarkCountKey = "bookmarkCount:" + trainerId;
            String ptCountKey = "ptCount:" + trainerId;
            String starKey = "star:" + trainerId;
            idList.add(trainerId);
            if (valueOperations.get(reviewCountKey).equals("0")) {
                starList.add(0f);
            } else {
                starList.add(Float.parseFloat(valueOperations.get(starKey)) / Float.parseFloat(valueOperations.get(reviewCountKey)));

            }
            reviewCountList.add(Double.parseDouble(valueOperations.get(reviewCountKey)));
            bookmarkList.add(Double.parseDouble(valueOperations.get(bookmarkCountKey)));
            PTList.add(Double.parseDouble(valueOperations.get(ptCountKey)));
        }
        List<TrainerDto.ResponseList> trainerList = new ArrayList<>();
        for (int idx = 0; idx < result.size(); idx++) {
            Trainer t = trainerRepository.findById(idList.get(idx)).get();
            List<Long> userProfileIdList = findUserProfileIdByBookmark(t.getId());
            TrainerDto.ResponseList trainer = TrainerDto.ResponseList.builder()
                    .id(t.getId())
                    .introduction(t.getIntroduction())
                    .tags(t.getTags())
                    .createdAt(t.getCreatedAt().toLocalDate())
                    .profileName(t.getUserProfile().getName())
                    .profileImg(t.getUserProfile().getProfileImage())
                    .stars(starList.get(idx))
                    .bookmarkCount(Double.valueOf(bookmarkList.get(idx)).intValue())
                    .ptCount(Double.valueOf(PTList.get(idx)).intValue())
                    .reviewCount(Double.valueOf(reviewCountList.get((idx))).intValue())
                    .userProfileIdList(userProfileIdList)
                    .build();

            trainerList.add(trainer);
        }

        return trainerList;
    }

    public List<TrainerDto.ResponseList> findSortedByReviewTrainers(int page, int size) {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Set<TypedTuple<String>> typedTuples;

        //String key = "ranking";
        String key = keyMap.get("리뷰 수");

        if (ZSetOperations.size(key) >= page * 10 + size) {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, page * 10, page * 10 + 9);  //score순으로 10개 보여줌
        } else {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, page * 10, ZSetOperations.size(key));
        }
        List<TrainerDto.Rank> result = typedTuples.stream().map(TrainerDto.Rank::convertToRank)
                .collect(
                        Collectors.toList());

        List<String> idList = new ArrayList<>();
        List<Float> starList = new ArrayList<>();
        List<Double> reviewCountList = new ArrayList<>();
        List<Double> PTList = new ArrayList<>();
        List<Double> bookmarkList = new ArrayList<>();
        for (TrainerDto.Rank rank : result) {
            String trainerId = rank.getId();
            String reviewCountKey = "reviewCount:" + trainerId;
            String bookmarkCountKey = "bookmarkCount:" + trainerId;
            String ptCountKey = "ptCount:" + trainerId;
            String starKey = "star:" + trainerId;
            idList.add(trainerId);
            if (valueOperations.get(reviewCountKey).equals("0")) {
                starList.add(0f);
            } else {
                starList.add(Float.parseFloat(valueOperations.get(starKey)) / Float.parseFloat(valueOperations.get(reviewCountKey)));

            }
            reviewCountList.add(Double.parseDouble(valueOperations.get(reviewCountKey)));
            bookmarkList.add(Double.parseDouble(valueOperations.get(bookmarkCountKey)));
            PTList.add(Double.parseDouble(valueOperations.get(ptCountKey)));
        }
        List<TrainerDto.ResponseList> trainerList = new ArrayList<>();
        for (int idx = 0; idx < result.size(); idx++) {
            Trainer t = trainerRepository.findById(idList.get(idx)).get();
            List<Long> userProfileIdList = findUserProfileIdByBookmark(t.getId());
            TrainerDto.ResponseList trainer = TrainerDto.ResponseList.builder()
                    .id(t.getId())
                    .introduction(t.getIntroduction())
                    .tags(t.getTags())
                    .createdAt(t.getCreatedAt().toLocalDate())
                    .profileName(t.getUserProfile().getName())
                    .profileImg(t.getUserProfile().getProfileImage())
                    .stars(starList.get(idx))
                    .bookmarkCount(Double.valueOf(bookmarkList.get(idx)).intValue())
                    .ptCount(Double.valueOf(PTList.get(idx)).intValue())
                    .reviewCount(Double.valueOf(reviewCountList.get((idx))).intValue())
                    .userProfileIdList(userProfileIdList)
                    .build();

            trainerList.add(trainer);
        }

        return trainerList;
    }

    public List<TrainerDto.ResponseList> findSortedByPtTrainers(int page, int size) {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Set<TypedTuple<String>> typedTuples;

        //String key = "ranking";
        String key = keyMap.get("누적 PT 수");

        if (ZSetOperations.size(key) >= page * 10 + size) {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, page * 10, page * 10 + 9);  //score순으로 10개 보여줌
        } else {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, page * 10, ZSetOperations.size(key));
        }
        List<TrainerDto.Rank> result = typedTuples.stream().map(TrainerDto.Rank::convertToRank)
                .collect(
                        Collectors.toList());

        List<String> idList = new ArrayList<>();
        List<Float> starList = new ArrayList<>();
        List<Double> reviewCountList = new ArrayList<>();
        List<Double> PTList = new ArrayList<>();
        List<Double> bookmarkList = new ArrayList<>();
        for (TrainerDto.Rank rank : result) {
            String trainerId = rank.getId();
            String reviewCountKey = "reviewCount:" + trainerId;
            String bookmarkCountKey = "bookmarkCount:" + trainerId;
            String ptCountKey = "ptCount:" + trainerId;
            String starKey = "star:" + trainerId;
            idList.add(trainerId);
            if (valueOperations.get(reviewCountKey).equals("0")) {
                starList.add(0f);
            } else {
                starList.add(Float.parseFloat(valueOperations.get(starKey)) / Float.parseFloat(valueOperations.get(reviewCountKey)));

            }
            reviewCountList.add(Double.parseDouble(valueOperations.get(reviewCountKey)));
            bookmarkList.add(Double.parseDouble(valueOperations.get(bookmarkCountKey)));
            PTList.add(Double.parseDouble(valueOperations.get(ptCountKey)));
        }
        List<TrainerDto.ResponseList> trainerList = new ArrayList<>();
        for (int idx = 0; idx < result.size(); idx++) {
            Trainer t = trainerRepository.findById(idList.get(idx)).get();
            List<Long> userProfileIdList = findUserProfileIdByBookmark(t.getId());

            TrainerDto.ResponseList trainer = TrainerDto.ResponseList.builder()
                    .id(t.getId())
                    .introduction(t.getIntroduction())
                    .tags(t.getTags())
                    .createdAt(t.getCreatedAt().toLocalDate())
                    .profileName(t.getUserProfile().getName())
                    .profileImg(t.getUserProfile().getProfileImage())
                    .stars(starList.get(idx))
                    .bookmarkCount(Double.valueOf(bookmarkList.get(idx)).intValue())
                    .ptCount(Double.valueOf(PTList.get(idx)).intValue())
                    .reviewCount(Double.valueOf(reviewCountList.get((idx))).intValue())
                    .userProfileIdList(userProfileIdList)
                    .build();

            trainerList.add(trainer);
        }

        return trainerList;
    }

    public List<TrainerDto.ResponseList> findSortedByBookmarkTrainers(int page, int size) {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Set<TypedTuple<String>> typedTuples;

        //String key = "ranking";
        String key = keyMap.get("즐겨찾기 수");

        if (ZSetOperations.size(key) >= page * 10 + size) {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, page * 10, page * 10 + 9);  //score순으로 10개 보여줌
        } else {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, page * 10, ZSetOperations.size(key));
        }
        List<TrainerDto.Rank> result = typedTuples.stream().map(TrainerDto.Rank::convertToRank)
                .collect(
                        Collectors.toList());

        List<String> idList = new ArrayList<>();
        List<Float> starList = new ArrayList<>();
        List<Double> reviewCountList = new ArrayList<>();
        List<Double> PTList = new ArrayList<>();
        List<Double> bookmarkList = new ArrayList<>();
        for (TrainerDto.Rank rank : result) {
            String trainerId = rank.getId();
            String reviewCountKey = "reviewCount:" + trainerId;
            String bookmarkCountKey = "bookmarkCount:" + trainerId;
            String ptCountKey = "ptCount:" + trainerId;
            String starKey = "star:" + trainerId;
            idList.add(trainerId);
            if (valueOperations.get(reviewCountKey).equals("0")) {
                starList.add(0f);
            } else {
                starList.add(Float.parseFloat(valueOperations.get(starKey)) / Float.parseFloat(valueOperations.get(reviewCountKey)));

            }
            reviewCountList.add(Double.parseDouble(valueOperations.get(reviewCountKey)));
            bookmarkList.add(Double.parseDouble(valueOperations.get(bookmarkCountKey)));
            PTList.add(Double.parseDouble(valueOperations.get(ptCountKey)));
        }
        List<TrainerDto.ResponseList> trainerList = new ArrayList<>();
        for (int idx = 0; idx < result.size(); idx++) {
            Trainer t = trainerRepository.findById(idList.get(idx)).get();
            List<Long> userProfileIdList = findUserProfileIdByBookmark(t.getId());

            TrainerDto.ResponseList trainer = TrainerDto.ResponseList.builder()
                    .id(t.getId())
                    .introduction(t.getIntroduction())
                    .tags(t.getTags())
                    .createdAt(t.getCreatedAt().toLocalDate())
                    .profileName(t.getUserProfile().getName())
                    .profileImg(t.getUserProfile().getProfileImage())
                    .stars(starList.get(idx))
                    .bookmarkCount(Double.valueOf(bookmarkList.get(idx)).intValue())
                    .ptCount(Double.valueOf(PTList.get(idx)).intValue())
                    .reviewCount(Double.valueOf(reviewCountList.get((idx))).intValue())
                    .userProfileIdList(userProfileIdList)
                    .build();

            trainerList.add(trainer);
        }

        return trainerList;
    }

    public Trainer deleteTrainer(String trainerId) {
        Trainer deletedTrainer = findVerifiedTrainer(trainerId);

        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        //트레이너 삭제시, 트레이너와 관련된 북마크 모두 삭제
        List<Bookmark> bookmarkList = bookmarkRepository.findByTrainerId(trainerId);
        for (Bookmark bookmark : bookmarkList) {
            bookmarkRepository.deleteById(bookmark.getId());
        }

        deletedTrainer.setIntroduction(null);
        deletedTrainer.setTags(null);
        deletedTrainer.setTitle(null);
        deletedTrainer.setContent(null);
        deletedTrainer.setImages(null);

        valueOperations.set("trainerCount", String.valueOf(Integer.parseInt(valueOperations.get("trainerCount")) - 1));

        Set<String> keys = redisTemplate.keys("*" + trainerId);

        for (String k : keyMap.values()) {
            ZSetOperations.remove(k, trainerId);
        }

        for (String s : keys) {
            listOperations.getOperations().delete(s);
            valueOperations.getOperations().delete(s);
        }
        return deletedTrainer;

    }

    public List<TrainerDto.ResponseList> searchTrainers(TrainerDto.Get search) {

        Set<Trainer> searchResultSet = new HashSet<>();
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        // 이름, 태그로 검색
        if (search.getKeyword() != null) {
            String keyword = search.getKeyword().trim();

            // 1. 트레이너 이름으로 검색
            if (trainerRepository.findByNameContaining(keyword).isPresent()) {
                searchResultSet.addAll(trainerRepository.findByNameContaining(keyword).get());
            }

            // 2. 트레이너 태그로 검색
            // 2-1. 태그 검색
            List<Tag> tagList = tagRepository.findByNameContaining(keyword).orElseThrow();
            // 2-2. 태그를 가진 트레이너 조회
            for (Tag tag : tagList) {
                String[] trainers = tag.getTrainers().split(",");
                for (String trainerId : trainers) {
                    searchResultSet.add(
                            trainerRepository.findById(trainerId).orElseThrow());
                }
            }
        }

        // 날짜, 시간으로 검색
        // 선택된 날짜, 시간에 가능한 수업 조회
        PtClassDto.Get getRequest = PtClassDto.Get.builder()
                .date(search.getDate())
                .fromTime(search.getFromTime())
                .toTime(search.getToTime())
                .build();

        List<PtClassDto.Response> classList = ptClassService.findPtClassByDatetime(getRequest);

        // 각 수업의 트레이너 조회
        for (PtClassDto.Response pt_class : classList) {
            searchResultSet.add(trainerRepository.findById(
                    pt_class.getTrainerId()).orElseThrow());
        }

        List<Trainer> searchResultList = searchResultSet.stream().toList();

        // 검색 결과
        List<TrainerDto.ResponseList> trainerList = new ArrayList<>();
        for (Trainer t : searchResultSet) {
            String trainerId = t.getId();

            String reviewCountKey = "reviewCount:" + trainerId;
            String starKey = "star:" + trainerId;
            String bookmarkCountKey = "bookmarkCount:" + trainerId;
            String ptCountKey = "ptCount:" + trainerId;

            List<Long> userProfileIdList = findUserProfileIdByBookmark(trainerId);
            float star = 0f;
            if (!valueOperations.get(reviewCountKey).equals("0")) {
                star = Float.parseFloat(valueOperations.get(starKey)) / Float.parseFloat(valueOperations.get(reviewCountKey));
            }
            TrainerDto.ResponseList trainer = TrainerDto.ResponseList.builder()
                    .id(t.getId())
                    .introduction(t.getIntroduction())
                    .tags(t.getTags())
                    .createdAt(t.getCreatedAt().toLocalDate())
                    .profileName(t.getUserProfile().getName())
                    .profileImg(t.getUserProfile().getProfileImage())
                    .stars(star)
                    .bookmarkCount(Double.valueOf(valueOperations.get(bookmarkCountKey)).intValue())
                    .ptCount(Double.valueOf(valueOperations.get(ptCountKey)).intValue())
                    .reviewCount(Double.valueOf(valueOperations.get(reviewCountKey)).intValue())
                    .revisitGrade(0)
                    .userProfileIdList(userProfileIdList)
                    .build();

            trainerList.add(trainer);
        }

        return trainerList;
    }
//            return trainerMapper.trainerListToResponseListDtoList(searchResultList);


    public Trainer findVerifiedTrainer(String trainerId) {

        Optional<Trainer> optionalTrainer = trainerRepository.findById(trainerId);

        Trainer findTrainer = optionalTrainer.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.TRAINER_NOT_FOUND));

        return findTrainer;
    }

    public PageInfo getPageInfo(int page, int size) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        int num = Integer.parseInt(valueOperations.get("trainerCount"));
        int pages = 0;
        if (num % size == 0) {
            pages = num / size;
        } else {
            pages = (num / size) + 1;

        }
        return new PageInfo(page, size, num, pages);
    }

    //북마크된 유저프로필 아이디 출력하는 메소드
    public List<Long> findUserProfileIdByBookmark(String trainerId) {
        List<Bookmark> bookmarkList = bookmarkRepository.findByTrainerId(trainerId);

        List<Long> userProfileIdList = new ArrayList<Long>();

        for (Bookmark bookmark : bookmarkList) {
            userProfileIdList.add(bookmark.getUserProfile().getId());
        }
        return userProfileIdList;
    }
}

//    public List<TrainerDto.ResponseList> findAllTrainers() {
//        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//        List<TrainerDto.ResponseList> trainerList = new ArrayList<>();
//        for (Trainer t : trainerRepository.findAll()) {
//            String trainerId = t.getId();
//
//            String starKey = "star:" + trainerId;
//            String reviewCountKey = "reviewCount:" + trainerId;
//            String ptCountKey = "ptCount:" + trainerId;
//            String bookmarkCountKey = "bookmarkCount:" + trainerId;
//            String newKey = "new:" + trainerId;
//
//            TrainerDto.ResponseList trainer = TrainerDto.ResponseList.builder()
//                    .id(t.getId())
//                    .introduction(t.getIntroduction())
//                    .tags(t.getTags())
//                    .createdAt(t.getCreatedAt().toLocalDate())
//                    .profileName(t.getUserProfile().getName())
//                    .profileImg(t.getUserProfile().getProfileImage())
//                    .stars(Float.parseFloat(valueOperations.get(starKey))/Float.parseFloat(valueOperations.get(reviewCountKey)))
//                    .bookmarkCount()(Integer.parseInt(valueOperations.get(bookmarkCountKey)))
//                    .ptCount(Integer.parseInt(valueOperations.get(ptCountKey)))
//                    .reviewCount(Integer.parseInt(valueOperations.get(reviewCountKey)))
//                    .build();
//
//            trainerList.add(trainer);
//        }
//
//        return trainerList;
//    }
