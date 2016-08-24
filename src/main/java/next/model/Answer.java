package next.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import core.web.taglibs.Functions;
import next.AbstractEntity;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Answer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;
	
	private String contents;
	
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_id"))
    private Question question;
	
	public Answer() {
    }
	
	public Answer(User writer, String contents, Question question) {
		this.writer = writer;
		this.contents = contents;
		this.question = question;
	}
	
	public User getWriter() {
		return writer;
	}
	
	public String getContents() {
		return contents;
	}
	
	public String getFormattedCreatedDate() {
		return Functions.formatLocalDateTime(getCreatedDate(), "yyyy-MM-dd HH:mm:ss");
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
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "Answer [answerId=" + getId() + ", writer=" + writer
				+ ", contents=" + contents + ", createdDate=" + getFormattedCreatedDate()
				+ ", questionId=" + question.getId() + "]";
	}
}
