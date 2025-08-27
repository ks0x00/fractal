package token;

public class DoubleToken extends Token {
	private double value;

	public DoubleToken() {
		super(DOUBLE);
	}

	public DoubleToken(double value) {
		super(DOUBLE);
		this.value = value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "DoubleToken[" + Double.toString(value) + "]";
	}
}
