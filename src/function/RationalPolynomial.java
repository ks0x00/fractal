package function;

import java.util.List;
import java.util.Vector;
import complex.Complex;
import token.*;

public class RationalPolynomial implements Function {
	private Polynomial num;
	private Polynomial denom;

	public RationalPolynomial() {
		num = null;
		denom = null;
	}

	public RationalPolynomial(double n) {
		num = new Polynomial(n);
		denom = null;
	}

	public RationalPolynomial(Complex n) {
		num = new Polynomial(n);
		denom = null;
	}

	public RationalPolynomial(Polynomial n) {
		num = n;
		denom = null;
	}

	public RationalPolynomial(Polynomial n, Polynomial d) {
		num = n;
		denom = d;
		reduce();
	}

	public RationalPolynomial(RationalPolynomial rp) {
		num = new Polynomial(rp.num);
		if (rp.denom == null)
			denom = null;
		else
			denom = new Polynomial(rp.denom);
	}

	public Polynomial getNumerator() {
		return num;
	}

	public Polynomial getDenominator() {
		return denom;
	}

	@Override
	public Complex eval(Complex z) {
		return denom == null ? num.eval(z) : Complex.div(num.eval(z), denom.eval(z));
	}

	@Override
	public Function diff() {
		return denom == null ? new RationalPolynomial((Polynomial) num.diff(), null)
				: new RationalPolynomial(
						Polynomial.sub(Polynomial.mul(num, (Polynomial) denom.diff()),
								Polynomial.mul((Polynomial) num.diff(), denom)),
						Polynomial.mul(denom, denom));
	}

	public void reduce() {
		num.reduce();
		if (denom != null) {
			denom.reduce();
			if (denom.getDegree() == 0) {
				num.div(denom.getCoef(0));
				denom = null;
			}
		}
	}

	public Function simplifiedFunction() {
		if (denom == null)
			return num;
		else
			return this;
	}

	public boolean isPolynomial() {
		return denom == null;
	}

	public void add(RationalPolynomial rp) {
		if (denom == null) {
			if (rp.denom == null) {
				num.add(rp.num);
			} else {
				num.mul(rp.denom);
				num.add(rp.num);
				denom = new Polynomial(rp.denom);
			}
		} else {
			if (rp.denom == null) {
				num.add(Polynomial.mul(denom, rp.num));
			} else {
				num.mul(rp.denom);
				num.add(Polynomial.mul(denom, rp.num));
				denom.mul(rp.denom);
			}
		}
		reduce();
	}

	public void sub(RationalPolynomial rp) {
		add(neg(rp));
	}

	public void mul(RationalPolynomial rp) {
		num.mul(rp.num);
		if (rp.denom != null) {
			if (denom == null)
				denom = new Polynomial(rp.denom);
			else
				denom.mul(rp.denom);
		}
		reduce();
	}

	public void div(RationalPolynomial rp) {
		if (rp.denom != null)
			num.mul(rp.denom);
		if (denom == null)
			denom = new Polynomial(rp.num);
		else
			denom.mul(rp.num);
		reduce();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RationalPolynomial) {
			RationalPolynomial rp = (RationalPolynomial) obj;
			if (denom == null)
				return rp.denom == null && num.equals(rp.num);
			else
				return denom.equals(rp.denom) && num.equals(rp.num);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return (num.isMonomial() ? num.toString() : "(" + num + ")") + "/"
				+ (denom.isSingle() ? denom.toString() : "(" + denom + ")");
	}

	public static RationalPolynomial neg(RationalPolynomial rp) {
		return rp.denom == null ? new RationalPolynomial(Polynomial.neg(rp.num), null)
				: new RationalPolynomial(Polynomial.neg(rp.num), new Polynomial(rp.denom));
	}

	public static RationalPolynomial add(RationalPolynomial p, RationalPolynomial q) {
		RationalPolynomial p0 = new RationalPolynomial(p);
		p0.add(q);
		return p0;
	}

	public static RationalPolynomial sub(RationalPolynomial p, RationalPolynomial q) {
		RationalPolynomial p0 = new RationalPolynomial(p);
		p0.sub(q);
		return p0;
	}

	public static RationalPolynomial mul(RationalPolynomial p, RationalPolynomial q) {
		RationalPolynomial p0 = new RationalPolynomial(p);
		p0.mul(q);
		return p0;
	}

	public static RationalPolynomial div(RationalPolynomial p, RationalPolynomial q) {
		RationalPolynomial p0 = new RationalPolynomial(p);
		p0.div(q);
		return p0;
	}

	private static boolean firstValidity(Vector<Token> tokens, char var) {
		String acceptedChars = "Ii" + var;
		int size = tokens.size();
		int operation = Token.PLUS | Token.MINUS | Token.STAR | Token.SLASH | Token.POWER;
		if (tokens.get(size - 1).isOfType(operation | Token.OPEN_PARENTHESIS))
			return false;
		int prev = 0;
		int curr;
		int depth = 0;
		for (int i = 0; i < size; i++) {
			Token token = tokens.get(i);
			curr = token.getType();
			if (token.isOfType(operation)) {
				if (prev == operation
						|| (prev == Token.OPEN_PARENTHESIS && (curr == Token.STAR || curr == Token.SLASH)))
					return false;
				prev = operation;
			} else {
				if (curr == Token.ALPHABET) {
					if (!((AlphabeticToken) token).contained(acceptedChars))
						return false;
				} else if (curr == Token.CLOSE_PARENTHESIS) {
					if (prev == operation || --depth < 0 || prev == Token.OPEN_PARENTHESIS)
						return false;
				} else if (curr == Token.OPEN_PARENTHESIS)
					depth++;
				prev = curr;
			}
		}
		return depth == 0;
	}

	private static void firstReduce(Vector<Token> tokens) {
		int prev = 0;
		for (int i = 0; i < tokens.size(); i++) {
			int curr = tokens.get(i).getType();
			if (curr == Token.PLUS) {
				if (prev == Token.OPEN_PARENTHESIS || prev == 0)
					tokens.remove(i);
			} else if (curr == Token.MINUS) {
				tokens.set(i, new IntegerToken(-1));
				if (prev != Token.OPEN_PARENTHESIS && prev != 0) {
					tokens.add(i, new Token('+'));
					i++;
				}
				prev = Token.INTEGER;
			} else if (curr == Token.STAR) {
				tokens.remove(i--);
			} else
				prev = curr;
		}
	}

	private static RationalPolynomial parseMultiplicativeTokens(List<Token> tokens) {
		RationalPolynomial p = new RationalPolynomial(1);
		int size = tokens.size();
		Token token;
		RationalPolynomial prev = null;
		for (int i = 0; i < size; i++) {
			token = tokens.get(i);
			if (token.isOfType(Token.INTEGER)) {
				if (prev != null)
					p.mul(prev);
				prev = new RationalPolynomial(((IntegerToken) token).getValue());
			} else if (token.isOfType(Token.DOUBLE)) {
				if (prev != null)
					p.mul(prev);
				prev = new RationalPolynomial(((DoubleToken) token).getValue());
			} else if (token.isOfType(Token.ALPHABET)) {
				AlphabeticToken at = (AlphabeticToken) token;
				if (prev != null)
					p.mul(prev);
				if (at.contained("iI")) {
					prev = new RationalPolynomial(new Complex(0, 1));
				} else {
					prev = new RationalPolynomial(new Polynomial(new Complex[] { new Complex(0), new Complex(1) }));
				}
			} else if (token.isOfType(Token.POWER)) {
				Token pt = tokens.get(++i);
				if (prev != null && pt.isOfType(Token.INTEGER) && ((IntegerToken) pt).isGreaterThan(-1)) {
					RationalPolynomial q = new RationalPolynomial(1);
					for (int j = ((IntegerToken) pt).getValue(); j > 0; j--)
						q.mul(prev);
					p.mul(q);
					prev = null;
				} else
					throw new RationalPolynomialFormatException();
			} else if (token.isOfType(Token.OPEN_PARENTHESIS)) {
				if (prev != null)
					p.mul(prev);
				int depth = 1;
				for (int j = i + 1; j < size; j++) {
					int temp = tokens.get(j).getType();
					if (temp == Token.OPEN_PARENTHESIS)
						depth++;
					else if (temp == Token.CLOSE_PARENTHESIS)
						depth--;
					if (depth == 0) {
						prev = parseAdditiveTokens(tokens.subList(i + 1, j));
						i = j;
						break;
					}
				}
			} else if (token.isOfType(Token.SLASH)) {
				if (prev != null)
					p.mul(prev);
				Token dt = tokens.get(++i);
				int j;
				if (dt.isOfType(Token.OPEN_PARENTHESIS)) {
					int depth = 1;
					for (j = i + 1; j < size; j++) {
						int temp = tokens.get(j).getType();
						if (temp == Token.OPEN_PARENTHESIS)
							depth++;
						else if (temp == Token.CLOSE_PARENTHESIS)
							depth--;
						if (depth == 0)
							break;
					}
				} else
					j = i;
				if (j + 1 < size && tokens.get(j + 1).getType() == Token.POWER)
					j += 2;
				prev = parseMultiplicativeTokens(tokens.subList(i, j + 1));
				p.div(prev);
				prev = null;
				i = j;
			}
		}
		if (prev != null)
			p.mul(prev);
		return p;
	}

	private static RationalPolynomial parseAdditiveTokens(List<Token> tokens) {
		RationalPolynomial p = new RationalPolynomial(0);
		int size = tokens.size();
		int depth = 0;
		int partStart = 0;
		for (int i = 0; i < size; i++) {
			int type = tokens.get(i).getType();
			if (type == Token.PLUS && depth == 0) {
				p.add(parseMultiplicativeTokens(tokens.subList(partStart, i)));
				partStart = i + 1;
			} else if (type == Token.OPEN_PARENTHESIS)
				depth++;
			else if (type == Token.CLOSE_PARENTHESIS)
				depth--;
		}
		p.add(parseMultiplicativeTokens(tokens.subList(partStart, size)));
		return p;
	}

	public static RationalPolynomial parseRationalPolynomial(String s, char var) {
		Vector<Token> tokens = Token.toTokens(s);
		if (tokens.size() == 0)
			return null;
		if (firstValidity(tokens, var)) {
			firstReduce(tokens);
			return parseAdditiveTokens(tokens);
		} else
			throw new RationalPolynomialFormatException();
	}
}
