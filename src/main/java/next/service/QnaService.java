package next.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.fp.Result;
import next.CannotOperateException;
import next.model.Question;
import next.model.User;
import next.repository.QuestionRepository;

@Service
@Transactional
public class QnaService {
    @Autowired
    private QuestionRepository questionRepository;

	public Question findById(long questionId) {
		return questionRepository.findOne(questionId);
	}

	public Result<Question, String> deleteQuestion(long questionId, User user) {
		Question question = questionRepository.findOne(questionId);
		if (question == null) {
			return Result.error("존재하지 않는 질문입니다.");
		}
		
		Result<Question, String> result = question.delete(user);
		return result.either(q -> {
			questionRepository.delete(q);
			return Result.ok(q);
		}, e -> Result.error(e));
	}

	public void updateQuestion(long questionId, Question newQuestion, User user) throws CannotOperateException {
		Question question = questionRepository.findOne(questionId);
        if (question == null) {
        	throw new EmptyResultDataAccessException("존재하지 않는 질문입니다.", 1);
        }
        
        if (!question.isSameUser(user)) {
            throw new CannotOperateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
        }
        
        question.update(newQuestion);
	}
}
