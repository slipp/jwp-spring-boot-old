package next.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import next.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long>{
    List<Answer> findByQuestionId(long questionId);
}
