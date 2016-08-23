package next.model;

import static next.model.AnswerTest.newAnswer;
import static next.model.UserTest.newUser;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import next.CannotOperateException;

public class QuestionTest {
	public static Question newQuestion(User writer) {
		return new Question(writer, "title", "contents", Lists.newArrayList());
	}
	
	public static Question newQuestion(User writer, List<Answer> answers) {
		return new Question(writer, "title", "contents", answers);
	}

	@Test(expected = CannotOperateException.class)
	public void canDelete_글쓴이_다르다() throws Exception {
		User user = newUser("javajigi");
		Question question = newQuestion(newUser("sanjigi"));
		question.canDelete(user);
	}

	@Test
	public void canDelete_글쓴이_같음_답변_없음() throws Exception {
		User user = newUser("javajigi");
		Question question = newQuestion(newUser("javajigi"));
		assertTrue(question.canDelete(user));
	}

	@Test
	public void canDelete_같은_사용자_답변() throws Exception {
		String userId = "javajigi";
		User user = newUser(userId);
		List<Answer> answers = Lists.newArrayList(newAnswer(user));
		Question question = newQuestion(user, answers);
		assertTrue(question.canDelete(user));
	}

	@Test(expected = CannotOperateException.class)
	public void canDelete_다른_사용자_답변() throws Exception {
		String userId = "javajigi";
		User user = newUser(userId);
		List<Answer> answers = Lists.newArrayList(newAnswer(user), newAnswer(newUser("sanjigi")));
		newQuestion(user, answers).canDelete(user);
	}
}
