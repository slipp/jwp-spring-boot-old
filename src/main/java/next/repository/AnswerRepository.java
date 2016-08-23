package next.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import next.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long>{
}
