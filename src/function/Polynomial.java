package function;

import java.util.Arrays;

import complex.Complex;

public class Polynomial implements Function {
	private Complex[] coef;
	private int degree;

	public Polynomial() {
		coef = null;
		degree = -1;
	}

	public Polynomial(double d) {
		this(new Complex(d, 0));
	}

	public Polynomial(Complex z) {
		this(new Complex[] { z });
	}

	public Polynomial(Complex[] c) {
		coef = c;
		degree = c.length - 1;
		reduce();
	}

	public Polynomial(Polynomial p) {
		degree = p.degree;
		coef = new Complex[degree + 1];
		for (int i = 0; i <= degree; i++)
			coef[i] = new Complex(p.coef[i]);
	}

	public void reduce() {
		while (coef[degree].isZero() && degree > 0)
			degree--;
		coef = Arrays.copyOf(coef, degree + 1);
	}

	public Complex[] getCoef() {
		return coef;
	}

	public Complex getCoef(int i) {
		return coef[i];
	}

	public int getDegree() {
		return degree;
	}

	public boolean isMonomial() {
		int nonzero = 0;
		for (int i = 0; i <= degree; i++)
			if (!coef[i].isZero() && ++nonzero > 1)
				return false;
		return true;
	}

	public boolean isSingle() {
		if (degree == 0)
			return coef[0].isReal() || coef[0].isEqualTo(new Complex(0, 1));
		int nonzero = 0;
		for (int i = 0; i <= degree; i++) {
			if (!coef[i].isZero() && (!coef[i].isEqualTo(1) || ++nonzero > 1))
				return false;
		}
		return true;
	}

	@Override
	public Complex eval(Complex z) {
		Complex value = new Complex(coef[degree]);
		for (int i = degree - 1; i >= 0; i--)
			value.mulAdd(z, coef[i]);
		return value;
	}

	@Override
	public Polynomial diff() {
		if (degree == 0)
			return new Polynomial(new Complex[] { new Complex(0, 0) });
		Complex[] dcoeff = new Complex[degree];
		for (int i = degree; i > 0; i--) {
			dcoeff[i - 1] = Complex.mul(i, coef[i]);
		}
		return new Polynomial(dcoeff);
	}

	public void neg() {
		for (int i = degree; i >= 0; i--)
			coef[i].neg();
	}

	public void add(Polynomial p) {
		if (degree >= p.degree) {
			for (int i = 0; i <= p.degree; i++)
				coef[i].add(p.coef[i]);
		} else {
			Complex[] newCoeff = new Complex[p.degree + 1];
			for (int i = 0; i <= p.degree; i++)
				newCoeff[i] = i <= degree ? Complex.add(coef[i], p.coef[i]) : new Complex(p.coef[i]);
			coef = newCoeff;
			degree = p.degree;
		}
		reduce();
	}

	public void sub(Polynomial p) {
		add(neg(p));
	}

	public void mul(Polynomial p) {
		int d = degree + p.degree;
		Complex[] c = new Complex[d + 1];
		for (int i = 0; i <= d; i++) {
			c[i] = new Complex(0, 0);
			for (int j = p.degree < i ? i - p.degree : 0; j <= (degree < i ? degree : i); j++)
				c[i].add(Complex.mul(coef[j], p.coef[i - j]));
		}
		degree = d;
		coef = c;
	}

	public void div(Complex c) {
		for (int i = 0; i <= degree; i++)
			coef[i].div(c);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Polynomial) {
			Polynomial p = (Polynomial) obj;
			if (degree == p.degree && degree >= 0) {
				for (int i = 0; i <= degree; i++)
					if (!coef[i].equals(p.coef[i]))
						return false;
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		if (coef == null)
			return "[null]";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <= degree; i++) {
			if (coef[i].isReal()) {
				if (coef[i].isImaginary())
					continue;
				else {
					if (coef[i].isEqualTo(1)) {
						if (i == 0)
							sb.append(1);
					} else
						sb.append(coef[i]);
				}
			} else if (coef[i].isImaginary())
				sb.append(coef[i]);
			else {
				if (i == 0)
					sb.append(coef[i]);
				else
					sb.append("(" + coef[i] + ")");
			}
			if (i >= 1) {
				sb.append("z");
				if (i > 1)
					sb.append("^" + i);
			}
			if (i != degree)
				sb.append("+");
		}
		if (sb.length() == 0)
			sb.append("0");
		return sb.toString();
	}

	public static Polynomial neg(Polynomial p) {
		Complex[] z = new Complex[p.degree + 1];
		for (int i = 0; i <= p.degree; i++)
			z[i] = Complex.neg(p.coef[i]);
		return new Polynomial(z);
	}

	public static Polynomial add(Polynomial p, Polynomial q) {
		Complex[] c;
		if (p.degree > q.degree) {
			c = new Complex[p.degree];
			for (int i = 0; i <= p.degree; i++)
				c[i] = i <= q.degree ? Complex.add(p.coef[i], q.coef[i]) : new Complex(p.coef[i]);
		} else {
			c = new Complex[q.degree];
			for (int i = 0; i <= q.degree; i++)
				c[i] = i <= p.degree ? Complex.add(p.coef[i], q.coef[i]) : new Complex(q.coef[i]);
		}
		return new Polynomial(c);
	}

	public static Polynomial sub(Polynomial p, Polynomial q) {
		return add(p, neg(q));
	}

	public static Polynomial mul(Polynomial p, Polynomial q) {
		int d = p.degree + q.degree;
		Complex[] c = new Complex[d + 1];
		for (int i = 0; i <= d; i++) {
			c[i] = new Complex(0, 0);
			for (int j = q.degree < i ? i - q.degree : 0; j <= (p.degree < i ? p.degree : i); j++)
				c[i].add(Complex.mul(p.coef[j], q.coef[i - j]));
		}
		return new Polynomial(c);
	}
}
