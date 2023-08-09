package com.tcha.trainer.service;


import com.tcha.pt_class.dto.PtClassDto;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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

    /*
            ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples;

        //String key = "ranking";
        String key = keyMap.get(STAR_KEY);

        if (ZSetOperations.size(key) >= 5) {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, 4);  //score순으로 5개 보여줌
        } else {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, ZSetOperations.size(key));
        }
//        List<TagRankDTO> result = typedTuples.stream().map(TagRankDTO::convertToTagRankDTO).collect(Collectors.toList());
        System.out.println(typedTuples);
     */

    public TrainerDto.Response createTrainer(long userProfileId, TrainerDto.Post postRequest) {

        // userProfile 객체 가져오기 (유효성 검증 로직 추가 :: 활성상태 유저인지 확인, 일반 유저인지 확인)
        UserProfile postUser = userProfileRepository.findById(userProfileId).orElseThrow();

        // 트레이너 생성 (권한 확인, 이미 트레이너라면 exceptioncode 날리기)
        Trainer createdTrainer = trainerRepository.save(
                trainerMapper.trainerPostDtoToTrainer(postRequest, postUser));

        // 태그 테이블 설정
        String trainerStr =
                createdTrainer.getId().toString() + ","; // 태그 trainers에 추가할 트레이너 아이디 문자열
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

        return trainerMapper.trainerToResponseDto(createdTrainer);
    }


    public TrainerDto.Response updateTrainer(String trainerId, TrainerDto.Patch patchRequest) {

        // 트레이너 유효성 검증 로직 추가
        Trainer trainer = trainerRepository.findById(UUID.fromString(trainerId)).orElseThrow();

        trainer.setIntroduction(patchRequest.getIntroduction());
        trainer.setTitle(patchRequest.getTitle());
        trainer.setContent(patchRequest.getContent());
        trainer.setTags(patchRequest.getTags());
        // 연결된 user 변경되지 않도록(setUserProfile 안되도록) 설정하기

        return trainerMapper.trainerToResponseDto(trainer);
    }

    public TrainerDto.Response findOneTrainer(String trainerId) {

        // 트레이너 유효성 검증 추가
        Trainer trainer = trainerRepository.findById(UUID.fromString(trainerId)).orElseThrow();

        return trainerMapper.trainerToResponseDto(trainer);
    }

    public List<TrainerDto.ResponseList> findAllTrainers() {

        // 유효한 트레이너만 가져올 수 있도록 쿼리 수정
        List<Trainer> trainerList = trainerRepository.findAll();

//        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
//        Set<TypedTuple<String>> typedTuples;
//
//        //String key = "ranking";
//        String key = keyMap.get("평균 별점");
//
//        System.out.println(key);
//        if (ZSetOperations.size(key) >= 5) {
//            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, 4);  //score순으로 5개 보여줌
//        } else {
//            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, ZSetOperations.size(key));
//        }
//        List<TrainerDto.Rank> result = typedTuples.stream().map(TrainerDto.Rank::convertToRank)
//                .collect(
//                        Collectors.toList());
//        System.out.println(result);

        return trainerMapper.trainerListToResponseListDtoList(trainerList);
    }

    public TrainerDto.Response deleteTrainer(String trainerId) {

        // 삭제하려는 트레이너 (유효성 검증 추가 & 현재 로그인한 유저와 트레이너에 연결된 유저가 동일한지 확인)
        Trainer deletedTrainer =
                trainerRepository.findById(UUID.fromString(trainerId)).orElseThrow();

        deletedTrainer.setTags(null);
        deletedTrainer.setIntroduction(null);
        deletedTrainer.setTitle(null);
        deletedTrainer.setContent(null);

        return trainerMapper.trainerToResponseDto(deletedTrainer);
    }

    public List<TrainerDto.ResponseList> searchTrainers(TrainerDto.Get search) {

        Set<Trainer> searchResultSet = new HashSet<>();

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
                            trainerRepository.findById(UUID.fromString(trainerId)).orElseThrow());
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
                    UUID.fromString(pt_class.getTrainerId())).orElseThrow());
        }

        List<Trainer> searchResultList = searchResultSet.stream().toList();

        return trainerMapper.trainerListToResponseListDtoList(searchResultList);
    }

}
