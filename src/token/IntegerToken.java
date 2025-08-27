package token;

public class IntegerToken extends Token {
	private int value;

	public IntegerToken() {
		super(INTEGER);
	}

	public IntegerToken(int value) {
		super(INTEGER);
		this.value = value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public boolean isGreaterThan(int d) {
		return value > d;
	}

	@Override
	public String toString() {
		return "IntegerToken[" + Integer.toString(value) + "]";
	}
}
