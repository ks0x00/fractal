package token;

import java.util.Vector;

public class Token {
	public static final int INTEGER = 0x1;
	public static final int DOUBLE = 0x2;
	public static final int ALPHABET = 0x4;
	public static final int PLUS = 0x8;
	public static final int MINUS = 0x10;
	public static final int STAR = 0x20;
	public static final int SLASH = 0x40;
	public static final int POWER = 0x80;
	public static final int OPEN_PARENTHESIS = 0x100;
	public static final int CLOSE_PARENTHESIS = 0x200;
	public static final int COMPLEX = 0x400;

	private int type;

	public Token() {
	}

	public Token(int type) {
		this.type = type;
	}

	public Token(char c) {
		switch (c) {
		case '+':
			type = PLUS;
			break;
		case '-':
			type = MINUS;
			break;
		case '*':
			type = STAR;
			break;
		case '/':
			type = SLASH;
			break;
		case '^':
			type = POWER;
			break;
		case '(':
			type = OPEN_PARENTHESIS;
			break;
		case ')':
			type = CLOSE_PARENTHESIS;
			break;
		default:
			throw new IncompatibleTokenDefinitionException(c);
		}
	}

	public int getType() {
		return type;
	}

	public boolean isOfType(int x) {
		return (x & type) != 0;
	}

	@Override
	public String toString() {
		return "Token[" + (type == POWER ? "^"
				: type == PLUS ? "+"
						: type == MINUS ? "-"
								: type == STAR ? "*" : type == SLASH ? "/" : type == OPEN_PARENTHESIS ? "(" : ")")
				+ "]";
	}

	public static Vector<Token> toTokens(String s) {
		StringBuffer sb = new StringBuffer(s.trim());
		int len = sb.length();
		Vector<Token> tokens = new Vector<>(2 * len);
		for (int i = 0; i < len; i++) {
			char c = sb.charAt(i);
			try {
				tokens.add(new Token(c));
				continue;
			} catch (IncompatibleTokenDefinitionException itde) {
			}
			try {
				tokens.add(new AlphabeticToken(c));
				continue;
			} catch (IncompatibleTokenDefinitionException itde) {
			}
			if (c == '.' || ('0' <= c && c <= '9')) {
				boolean dot = (c == '.');
				char d;
				int j = i + 1;
				for (; j < len; j++) {
					d = sb.charAt(j);
					if (d == '.') {
						if (dot)
							break;
						else
							dot = true;
					} else if (d < '0' || '9' < d)
						break;
				}
				if (dot)
					tokens.add(new DoubleToken(Double.parseDouble(sb.substring(i, j))));
				else
					tokens.add(new IntegerToken(Integer.parseInt(sb.substring(i, j))));
				i = j - 1;
			} else if (c != ' ')
				throw new UnknownTokenException(c);
		}
		tokens.trimToSize();
		return tokens;
	}
}
