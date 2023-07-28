//package com.tcha.bookmark.service;
//
//
//import com.tcha.bookmark.entity.Bookmark;
//import com.tcha.bookmark.mapper.BookmarkMapper;
//import com.tcha.bookmark.repository.BookmarkRepository;
//import com.tcha.trainer.entity.Trainer;
//import com.tcha.trainer.repository.TrainerRepository;
//import com.tcha.user_profile.entity.UserProfile;
//import com.tcha.user_profile.repository.UserProfileRepository;
//import jakarta.transaction.Transactional;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//@Slf4j
//public class BookmarkService {
//
//    private final UserProfileRepository userProfileRepository;
//    private final TrainerRepository trainerRepository;
//    private final BookmarkMapper bookmarkMapper;
//    private final BookmarkRepository bookMarkRepository;
//
//    //최초 즐겨찾기 등록
//    public Bookmark createBookmark(Long userProfileId, String trainerId) {
//        //유저 객체 가져오기
////        UserProfile userProfile = userProfileRepository.findById(userProfileId);
//
//        //트레이너 객체 가져오기
//        Trainer trainer = trainerRepository.findById(trainerId);
//
//        //새로운 즐겨찾기 객체 만들기
//        Bookmark bookmark = bookMarkRepository.save(bookmarkMapper.makeBookmark(userProfile, trainer));
//
//        return bookMarkRepository.save(bookmark);
//    }


//    //즐겨찾기에서 삭제
//    public int deleteBookmark(Long id) {
//        return bookMarkRepository.updateBookMarkStatus(id);
//    }
//
//
//    //유저별 즐겨찾기 목록 확인
//    public List<BookmarkDto.Response> findAllUserIdBookMark(UUID userId) {
//        List<Bookmark> responseBookMark = bookMarkRepository.findByUserId(userId);
//        List<BookmarkDto.Response> response = new ArrayList<>();
//
//        for (Bookmark now : responseBookMark) {
//            BookmarkDto.Response ansGuide = BookmarkDto.Response.builder()
//                    .id(now.getId())
//                    .trainer(now.getTrainer())
//                    .user(now.getUser()).build();
//            response.add(ansGuide);
//        }
//        return response;
//    }

//}
