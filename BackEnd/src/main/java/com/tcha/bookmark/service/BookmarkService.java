package com.tcha.bookmark.service;


import com.tcha.bookmark.dto.BookmarkDto;
import com.tcha.bookmark.entity.Bookmark;
import com.tcha.bookmark.mapper.BookmarkMapper;
import com.tcha.bookmark.repository.BookmarkRepository;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
import com.tcha.utils.exceptions.business.BusinessLogicException;
import com.tcha.utils.exceptions.codes.ExceptionCode;
import com.tcha.utils.pagination.MultiResponseDto;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookmarkService {

    private final UserProfileRepository userProfileRepository;
    private final TrainerRepository trainerRepository;
    private final BookmarkMapper bookmarkMapper;
    private final BookmarkRepository bookMarkRepository;
    private final RedisTemplate<String, String> redisTemplate;

    //최초 즐겨찾기 등록
    public BookmarkDto.Response createBookmark(Long userProfileId, String trainerId) {

        //유저프로필 객체 가져오기
        UserProfile userProfile = findVerifiedUserProfileById(userProfileId);

        //트레이너 객체 가져오기
        Trainer trainer = findVerifiedTrainerById(trainerId);

        //새로운 즐겨찾기 entity객체 만들어서 저장하기
        Bookmark bookmark = bookMarkRepository.save(Bookmark.builder()
                .userProfile(userProfile)
                .trainer(trainer).build());

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();
        String bookmarkCountKey = "bookmarkCount:" + trainerId;

        String s = valueOperations.get(bookmarkCountKey);
        valueOperations.set(bookmarkCountKey,String.valueOf(Double.parseDouble(s) + 1.0));
        ZSetOperations.add("bookmark", trainerId, Double.parseDouble(s) + 1.0);

        return bookmarkMapper.bookMarkToBookMarkDtoResponse(bookmark);
    }

    //즐겨찾기에서 삭제, deleteById의 경우 내부 로직으로 null값에 대한 에러처리가 이뤄지고 있음
    public void deleteBookmark(Long id) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();

        String trainerId = bookMarkRepository.findById(id).get().getTrainer().getId();
        String bookmarkCountKey = "bookmarkCount:" + trainerId;

        String s = valueOperations.get(bookmarkCountKey);
        valueOperations.set(bookmarkCountKey,String.valueOf(Double.parseDouble(s) - 1.0));
        ZSetOperations.add("bookmark", trainerId, Double.parseDouble(s) - 1.0);

        bookMarkRepository.deleteById(id);
    }


    //유저별 즐겨찾기 목록 확인
    public MultiResponseDto<BookmarkDto.Response> findAllUserIdBookMark(Integer page, Integer size,
            Long userProfileId) {

        //유저프로필 객체 가져오기
        UserProfile userProfile = findVerifiedUserProfileById(userProfileId);

        //해당 유저프로필 객체의 즐겨찾기 목록 가져오기
        Page<Bookmark> pageBookMarks = bookMarkRepository.findByUserProfile(userProfile,
                PageRequest.of(page - 1, size, Sort.by("id").descending()));

        List<BookmarkDto.Response> Bookmarks = bookmarkMapper.bookmarkListToBookmarkDtoResponse(
                pageBookMarks.getContent());

        return new MultiResponseDto<BookmarkDto.Response>(Bookmarks, pageBookMarks);
    }

    //유저 프로필 Id와 트레이너 Id를 이용하여 북마크 조회하기
    public BookmarkDto.Response getFindBookmarkIdByUserProfileIdAndTrainerId(
            Long userProfileId,
            String trainerId) {

        //유저프로필 객체 가져오기
        UserProfile userProfile = findVerifiedUserProfileById(userProfileId);

        //트레이너 객체 가져오기
        Trainer trainer = findVerifiedTrainerById(trainerId);

        //유저프로필과 트레이너 객체 이용해서 북마크 객체 가져오기
        Bookmark bookmark = findVerifiedBookmarkByUserProfileAndTrainer(userProfileId, trainerId);

        return bookmarkMapper.bookMarkToBookMarkDtoResponse(bookmark);

    }

    //유저프로필 아이디와 트레이너 아이디를 이용하여 북마크 삭제하기
    public void deleteBookmarkByUserProfileIdAndTrainerId(Long userProfileId,
            String trainerId) {
        Bookmark bookmark = findVerifiedBookmarkByUserProfileAndTrainer(userProfileId, trainerId);
        bookMarkRepository.deleteById(bookmark.getId());
    }

    //유저 프로필아이디와 트레이너 아이디로 북마크 조회시 유효성 검증
    public Bookmark findVerifiedBookmarkByUserProfileAndTrainer(Long userProfile,
            String trainerId) {
        Bookmark bookmark = bookMarkRepository.findBookmarkByUserProfileAndTrainer(userProfile,
                        trainerId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOOKMARK_NOT_FOUND));
        return bookmark;
    }

    //존재하는 유저 프로필인지에 대한 유효성 검증
    public UserProfile findVerifiedUserProfileById(Long userProfileId) {

        //userProfileId 타입 변환
//        Long userProfileId = ConversionUtil.stringToLong(stringUserProfileId, 0L);

        //유저프로필 객체 가져오기
        UserProfile userProfile = userProfileRepository.findById(userProfileId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_PROFILE_NOT_FOUND));

        return userProfile;
    }

    //존재하는 트레이너인지 대한 유효성 검증
    public Trainer findVerifiedTrainerById(String trainerId) {

        //trainerId 타입 변환
//        UUID trainerId = ConversionUtil.stringToUUID(stringTrainerId,
//                UUID.fromString("00000000-0000-0000-0000-000000000000"));

        //트레이너 객체 가져오기
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.TRAINER_NOT_FOUND));

        return trainer;
    }
}
