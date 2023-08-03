package com.tcha.answer.servicce;

import com.tcha.answer.entity.Answer;
import com.tcha.answer.repository.AnswerRepository;
import com.tcha.question.entity.Question;
import com.tcha.question.servicce.QuestionService;
import com.tcha.user_profile.service.UserProfileService;
import com.tcha.utils.exceptions.business.BusinessLogicException;
import com.tcha.utils.exceptions.codes.ExceptionCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AnswerService {

    private final QuestionService questionService;
    private final UserProfileService userProfileService;
    private final AnswerRepository answerRepository;

    public Answer createAnswer(Answer answer) {

        verifyAnswer(answer);

        return answerRepository.save(answer);

    }

    public Answer findOneAnswer(Long answerId) {

        return findVerifiedAnswer(answerId);

    }

    public void deleteAnswer(Long answerId) {

        Answer answer = findVerifiedAnswer(answerId);

        answerRepository.delete(answer);

    }

    public Answer findVerifiedAnswer(Long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);

        Answer findAnswer = optionalAnswer.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        return findAnswer;
    }

    private void verifyAnswer(Answer answer) {

        userProfileService.findVerifiedUserProfile(answer.getUserProfile().getId());
        questionService.findVerifiedQuestion(answer.getQuestion().getId());
        
    }

}
