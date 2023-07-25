//package com.tcha.bookmark.service;
//
//import com.tcha.bookmark.dto.BookMarkDto;
//import com.tcha.bookmark.entity.BookMark;
//import com.tcha.bookmark.entity.BookMark.Status;
//import com.tcha.bookmark.repository.BookMarkRepository;
//import jakarta.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.stereotype.Service;
//
//@Service
//@Transactional
//public class BookMarkService {
//
//    private BookMarkRepository bookMarkRepository;
//
//    //최초 즐겨찾기 등록
//    public BookMark registBookMark(BookMark postBookMark, Long trainerId, String userId) {
//
//        Optional<BookMark> dupChekResponse = userIdAndTrainerIdIsExist(trainerId, userId);
//
//        //최초 즐겨찾기 등록이 아닌 경우
//        if (dupChekResponse.isPresent()) {
//            if (dupChekResponse.get().getStatus().equals(Status.STATUS_INACTIVE))
//            //status값 활성 상태로 변경
//            {
//                bookMarkRepository.updateBookMarkStatus(dupChekResponse.get().getId(),
//                        Status.STATUS_ACTIVE);
//            }
//            dupChekResponse.get().setStatus(Status.STATUS_ACTIVE);
//            return dupChekResponse.get();
//        }
//        //최초 즐겨찾기 등록인 경우
//        else {
//            //해당 데이터 저장
//            return bookMarkRepository.save(postBookMark);
//        }
//    }
//
//    //이미 즐겨찾기에 등록했었다가 상태값만 비활성인 경우
//
//    //즐겨찾기 해제
//    public int patchBookMark(BookMark patchBookMark) {
//        Optional<BookMark> dupChekResponse = bookMarkRepository.findById(patchBookMark.getId());
//        if (dupChekResponse.isPresent()) {
//            return bookMarkRepository.updateBookMarkStatus(patchBookMark.getId(),
//                    Status.STATUS_INACTIVE);
//        } else {
//            //존재하지 않는 즐겨찾기를 해제한 경우에 대한 예외처리 필요
//            return 0;
//        }
//    }
//
//    //유저별 즐겨찾기 목록 확인
//    public List<BookMarkDto.Response> findAllUserIdBookMark(String userId) {
//        List<BookMark> responseBookMark = bookMarkRepository.findByUserId(userId);
//        List<BookMarkDto.Response> response = new ArrayList<>();
//
//        for (BookMark now : responseBookMark) {
//            BookMarkDto.Response ansGuide = BookMarkDto.Response.builder()
//                    .id(now.getId())
//                    .trainer(now.getTrainer())
//                    .User(now.getUser())
//                    .createAt(now.getCreateAt())
//                    .status(String.valueOf(now.getStatus())).build();
//            response.add(ansGuide);
//        }
//        return response;
//    }
//
//    //유저 id와 트레이너 id를 가지고, 중복 체크
//    public Optional<BookMark> userIdAndTrainerIdIsExist(Long trainerId, String userId) {
//        return bookMarkRepository.findByTrainerIdAndUserId(trainerId, userId);
//    }
//}
