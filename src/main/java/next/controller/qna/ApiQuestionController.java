package next.controller.qna;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import core.web.argumentresolver.LoginUser;
import next.CannotOperateException;
import next.model.Answer;
import next.model.Question;
import next.model.Result;
import next.model.User;
import next.repository.AnswerRepository;
import next.repository.QuestionRepository;
import next.service.QnaService;

@RestController
@RequestMapping("/api/questions")
public class ApiQuestionController {
    private Logger log = LoggerFactory.getLogger(ApiQuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QnaService qnaService;

    @RequestMapping(value = "/{questionId}", method = RequestMethod.DELETE)
    public Result deleteQuestion(@LoginUser User loginUser, @PathVariable long questionId) throws Exception {
        try {
            qnaService.deleteQuestion(questionId, loginUser);
            return Result.ok();
        } catch (CannotOperateException e) {
            return Result.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Question> list() throws Exception {
        return questionRepository.findAll();
    }

    @RequestMapping(value = "/{questionId}/answers", method = RequestMethod.POST)
    public Map<String, Object> addAnswer(@LoginUser User loginUser, @PathVariable long questionId, String contents)
            throws Exception {
        log.debug("questionId : {}, contents : {}", questionId, contents);
        Map<String, Object> values = Maps.newHashMap();
        Answer answer = new Answer(loginUser.getUserId(), contents, questionId);
        Answer savedAnswer = answerRepository.save(answer);
        Question question = questionRepository.findOne(questionId);
        question.updateCountOfAnswer();
//        questionRepository.save(question);
//        questionDao.updateCountOfAnswer(savedAnswer.getQuestionId());

        values.put("answer", savedAnswer);
        values.put("result", Result.ok());
        return values;
    }

    @RequestMapping(value = "/{questionId}/answers/{answerId}", method = RequestMethod.DELETE)
    public Result deleteAnswer(@LoginUser User loginUser, @PathVariable long answerId) throws Exception {
        Answer answer = answerRepository.findOne(answerId);
        if (!answer.isSameUser(loginUser)) {
            return Result.fail("다른 사용자가 쓴 글을 삭제할 수 없습니다.");
        }

        try {
            answerRepository.delete(answer);
            return Result.ok();
        } catch (DataAccessException e) {
            return Result.fail(e.getMessage());
        }
    }
}
