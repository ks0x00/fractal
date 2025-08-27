package token;

@SuppressWarnings("serial")
public class UnknownTokenException extends RuntimeException {
	public UnknownTokenException() {
		super("Unknown Token Found");
	}

	public UnknownTokenException(char c) {
		super("Unknown Token " + c + " Found");
	}
}
