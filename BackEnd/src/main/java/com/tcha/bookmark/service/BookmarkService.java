package com.tcha.bookmark.service;


import com.tcha.bookmark.dto.BookmarkDto;
import com.tcha.bookmark.entity.Bookmark;
import com.tcha.bookmark.mapper.BookmarkMapper;
import com.tcha.bookmark.repository.BookmarkRepository;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
import com.tcha.utils.pagination.MultiResponseDto;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    //최초 즐겨찾기 등록
    public BookmarkDto.Response createBookmark(Long userProfileId, String trainerId) {
        //유저프로필 객체 가져오기
        UserProfile userProfile = userProfileRepository.findById(userProfileId).get();

        //트레이너 객체 가져오기
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow();

        //새로운 즐겨찾기 entity객체 만들어서 저장하기
        Bookmark bookmark = bookMarkRepository.save(Bookmark.builder()
                .userProfile(userProfile)
                .trainer(trainer).build());

        return bookmarkMapper.bookMarkToBookMarkDtoResponse(bookmark);
    }


    //즐겨찾기에서 삭제
    public void deleteBookmark(Long id) {
        bookMarkRepository.deleteById(id);
    }


    //유저별 즐겨찾기 목록 확인
    public MultiResponseDto<BookmarkDto.Response> findAllUserIdBookMark(Integer page, Integer size,
            Long userProfileId) {

        //유저프로필 객체 가져오기
        UserProfile userProfile = userProfileRepository.findById(userProfileId).get();

        //해당 유저프로필 객체의 즐겨찾기 목록 가져오기
        Page<Bookmark> pageBookMarks = bookMarkRepository.findByUserProfile(userProfile,
                PageRequest.of(page - 1, size, Sort.by("id").descending()));
        List<BookmarkDto.Response> Bookmarks = bookmarkMapper.bookmarkListToBookmarkDtoResponse(
                pageBookMarks.getContent());

        return new MultiResponseDto<BookmarkDto.Response>(Bookmarks, pageBookMarks);
    }

}
