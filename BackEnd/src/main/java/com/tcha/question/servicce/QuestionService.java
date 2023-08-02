package com.tcha.question.servicce;

import com.tcha.question.entity.Question;
import com.tcha.question.repository.QuestionRepository;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.service.UserProfileService;
import com.tcha.utils.exceptions.business.BusinessLogicException;
import com.tcha.utils.exceptions.codes.ExceptionCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final UserProfileService userProfileService;
    private final QuestionRepository questionRepository;

    public Question createQuestion(Long userProfileId, Question question) {

        UserProfile userProfile = userProfileService.findVerifiedUserProfile(userProfileId);
        question.setUserProfile(userProfile);

        return questionRepository.save(question);

    }

    public Question findOneQuestion(Long questionId) {

        return findVerifiedQuestion(questionId);

    }

    public Page<Question> findAllQuestions(int page, int size) {

        Page<Question> pageQuestions = questionRepository.findAll(
                PageRequest.of(page, size, Sort.by("id").descending()));

        return pageQuestions;

    }

    public void deleteQuestion(Long questionId) {

        Question question = findVerifiedQuestion(questionId);

        questionRepository.delete(question);

    }

    public Question findVerifiedQuestion(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        Question findQuestion = optionalQuestion.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        return findQuestion;
    }

}
