package next.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

import core.fp.Result;
import core.web.taglibs.Functions;
import next.AbstractEntity;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Question extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

	private String title;

	private String contents;
	
	private int countOfComment;
	
	private int showCount;
	
	@JsonIgnore
	@OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    @OrderBy("id ASC")
    private List<Answer> answers;

	public Question() {
	}

	public Question(User writer, String title, String contents) {
		this(writer, title, contents, Lists.newArrayList());
	}
	
	public Question(User writer, String title, String contents, List<Answer> answers) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.answers = answers;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public User getWriter() {
		return writer;
	}

	public int getCountOfComment() {
		return countOfComment;
	}
	
	public int getShowCount() {
		return showCount;
	}
	
	public Question newQuestion(User user) {
		return new Question(user, title, contents);
	}
	
	public boolean isSameUser(User user) {
		return user.isSameUser(this.writer);
	}
	
	public List<Answer> getAnswers() {
		return answers;
	}
	
	public String getFormattedCreatedDate() {
		return Functions.formatLocalDateTime(getCreatedDate(), "yyyy-MM-dd HH:mm:ss");
	}

	public void update(Question newQuestion) {
		this.title = newQuestion.title;
		this.contents = newQuestion.contents;
	}
	
    public void updateCountOfAnswer() {
        this.countOfComment += 1;
    }
    
    public Result<Question, String> delete(User loginUser) {
    	if (!writer.isSameUser(loginUser)) {
    		return Result.error("다른 사용자가 쓴 글을 삭제할 수 없습니다.");
		}
    	
		if( answers.stream().filter(a -> !a.isSameUser(loginUser)).count() > 0 ) {
			return Result.error("다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.");
		}
		
		return Result.ok(this);
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
		return "Question [questionId=" + getId() + ", writer=" + writer + ", title=" + title + ", contents="
				+ contents + ", createdDate=" + getFormattedCreatedDate() + ", countOfComment=" + countOfComment + "]";
	}
}
