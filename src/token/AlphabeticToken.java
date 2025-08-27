package token;

public class AlphabeticToken extends Token {
	char value;

	public AlphabeticToken() {
		super(ALPHABET);
	}

	public AlphabeticToken(char c) {
		super(ALPHABET);
		if (('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z'))
			value = c;
		else
			throw new IncompatibleTokenDefinitionException(c);
	}

	public void setValue(char c) {
		value = c;
	}

	public char getValue() {
		return value;
	}

	public boolean equals(char c) {
		return value == c;
	}

	public boolean contained(CharSequence s) {
		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) == value)
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "AlphabeticToken[" + Character.toString(value) + "]";
	}
}
