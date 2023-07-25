package com.tcha.bookmark.service;


import com.tcha.bookmark.dto.BookmarkDto;
import com.tcha.bookmark.entity.Bookmark;
import com.tcha.bookmark.repository.BookmarkRepository;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class BookmarkService {

    private BookmarkRepository bookMarkRepository;

    //최초 즐겨찾기 등록
    public Bookmark createBookmark(Bookmark postBookMark) {
        return bookMarkRepository.save(postBookMark);
    }

    //이미 즐겨찾기에 등록했었다가 상태값만 비활성인 경우

    //즐겨찾기 해제
    public int deleteBookmark(Long id) {
        return bookMarkRepository.updateBookMarkStatus(id);
    }


    //유저별 즐겨찾기 목록 확인
    public List<BookmarkDto.Response> findAllUserIdBookMark(UUID userId) {
        List<Bookmark> responseBookMark = bookMarkRepository.findByUserId(userId);
        List<BookmarkDto.Response> response = new ArrayList<>();

        for (Bookmark now : responseBookMark) {
            BookmarkDto.Response ansGuide = BookmarkDto.Response.builder()
                    .id(now.getId())
                    .trainer(now.getTrainer())
                    .user(now.getUser()).build();
            response.add(ansGuide);
        }
        return response;
    }

}
