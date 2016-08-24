package next.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long answerId;
	
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;
	
	private String contents;
	
	@CreatedDate
	private LocalDateTime createdDate;
	
	@LastModifiedDate 
	private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_id"))
    private Question question;
	
	public Answer() {
    }
	
	public Answer(User writer, String contents, Question question) {
		this(writer, contents, LocalDateTime.now(), question);
	}
	
	public Answer(User writer, String contents, LocalDateTime createdDate, Question question) {
		this.writer = writer;
		this.contents = contents;
		this.createdDate = LocalDateTime.now();
		this.question = question;
	}
	
	public long getAnswerId() {
		return answerId;
	}
	
	public User getWriter() {
		return writer;
	}
	
	public String getContents() {
		return contents;
	}
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
	public Question getQuestion() {
		return question;
	}
	
	public boolean isSameUser(User user) {
		if (user == null) {
			return false;
		}
		return user.isSameUser(this.writer);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (answerId ^ (answerId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		if (answerId != other.answerId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Answer [answerId=" + answerId + ", writer=" + writer
				+ ", contents=" + contents + ", createdDate=" + createdDate
				+ ", questionId=" + question.getQuestionId() + "]";
	}
}
