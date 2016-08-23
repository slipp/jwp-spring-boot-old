package next.model;

public class AnswerTest {
	public static Answer newAnswer(User writer) {
		return new Answer(writer, "contents", new Question());
	}
}
