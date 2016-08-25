package next.model;

public class JsonResult {
	private boolean status;
	private String message;

	private JsonResult(boolean status) {
		this(status, "");
	}
	
	private JsonResult(boolean status, String message) {
		this.status = status;
		this.message = message;
	}

	public static JsonResult ok() {
		return new JsonResult(true);
	}

	public static JsonResult fail(String message) {
		return new JsonResult(false, message);
	}

	public boolean isStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "Result [status=" + status + ", message=" + message + "]";
	}
}
