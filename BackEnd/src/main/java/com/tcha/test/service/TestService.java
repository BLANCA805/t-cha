package com.tcha.test.service;


import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.test.entity.Test;
import com.tcha.test.repository.TestRepository;
import com.tcha.trainer.dto.TrainerDto;
import com.tcha.trainer.dto.TrainerDto.Rank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final TestRepository testRepository;
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


    public Test createTest(Test test
    ) {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();

        ZSetOperations.add(keyMap.get("평균 별점"), test.getTitle(),
                test.getStar());

        return testRepository.save(test);
    }

    @Transactional(readOnly = true)
    public Page<Test> findTestPages(int page, int size) {
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples;

        //String key = "ranking";
        String key = keyMap.get(STAR_KEY);

        if (ZSetOperations.size(key) >= 5) {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, 4);  //score순으로 5개 보여줌
        } else {
            typedTuples = ZSetOperations.reverseRangeWithScores(key, 0, ZSetOperations.size(key));
        }
        System.out.println(typedTuples);

        return testRepository.findAll(

                PageRequest.of(page - 1, size, Sort.by("id").descending()));
    }
}
