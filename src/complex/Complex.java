package complex;

import java.util.StringTokenizer;

public class Complex {
	private static final double EPSILON = 1E-15;

	private double x;
	private double y;

	public Complex() {
	}

	public Complex(double x) {
		this.x = x;
		y = 0;
	}

	public Complex(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Complex(Complex z) {
		x = z.x;
		y = z.y;
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void set(Complex z) {
		x = z.x;
		y = z.y;
	}

	public double sqrNorm() {
		return x * x + y * y;
	}

	public boolean isBoundedBy(double ix, double iy, double ex, double ey) {
		return ix <= x && x <= ex && iy <= y && y <= ey;
	}

	public boolean isZero() {
		return isZero(x) && isZero(y);
	}

	public boolean isReal() {
		return isZero(y);
	}

	public boolean isImaginary() {
		return isZero(x);
	}

	public boolean isEqualTo(double d) {
		return isEqualTo(x, d) && isZero(y);
	}

	public boolean isEqualTo(Complex z) {
		return isEqualTo(x, z.x) && isEqualTo(y, z.y);
	}

	public void neg() {
		x = -x;
		y = -y;
	}

	public void add(Complex z) {
		x += z.x;
		y += z.y;
	}

	public void sub(Complex z) {
		x -= z.x;
		y -= z.y;
	}

	public void mul(double d) {
		x *= d;
		y *= d;
	}

	public void mul(Complex z) {
		double temp = x;
		x = temp * z.x - y * z.y;
		y = temp * z.y + y * z.x;
	}

	public void mulAdd(Complex z, Complex w) {
		double temp = x;
		x = temp * z.x - y * z.y + w.x;
		y = temp * z.y + y * z.x + w.y;
	}

	public void div(double d) {
		x /= d;
		y /= d;
	}

	public void div(Complex z) {
		double temp = x;
		double d = z.x * z.x + z.y * z.y;
		x = (temp * z.x + y * z.y) / d;
		y = (y * z.x - temp * z.y) / d;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Complex) {
			Complex c = (Complex) obj;
			return x == c.x && y == c.y;
		}
		return false;
	}

	public String toString() {
		if (isReal())
			return truncatedString(x);
		else if (isImaginary())
			return (isZero(y - 1) ? "" : truncatedString(y)) + "I";
		else
			return truncatedString(x) + "+" + (isZero(y - 1) ? "" : truncatedString(y)) + "I";
	}

	public static Complex neg(Complex z) {
		return new Complex(-z.x, -z.y);
	}

	public static Complex add(Complex z, Complex w) {
		return new Complex(z.x + w.x, z.y + w.y);
	}

	public static Complex sub(Complex z, Complex w) {
		return new Complex(z.x - w.x, z.y - w.y);
	}

	public static Complex mul(double d, Complex z) {
		return new Complex(d * z.x, d * z.y);
	}

	public static Complex mul(Complex z, Complex w) {
		return new Complex(z.x * w.x - z.y * w.y, z.x * w.y + z.y * w.x);
	}

	public static Complex div(Complex z, Complex w) {
		double d = w.x * w.x + w.y * w.y;
		return new Complex((z.x * w.x + z.y * w.y) / d, (z.y * w.x - z.x * w.y) / d);
	}

	public static boolean isZero(double d) {
		return -EPSILON < d && d < EPSILON;
	}

	public static boolean isEqualTo(double x, double y) {
		return x - EPSILON < y && y < x + EPSILON;
	}

	public static String truncatedString(double d) {
		String s = Double.toString(d);
		StringTokenizer st = new StringTokenizer(s, "Ee.");
		s = st.nextToken();
		String t = "1" + st.nextToken();
		long l;
		if (t.length() > 15)
			l = (Long.parseLong(t.substring(0, 16)) + 5) / 10;
		else
			l = Long.parseLong(t);
		while (l % 10 == 0)
			l /= 10;
		t = Long.toString(l);
		if (t.length() > 1)
			s += "." + t.substring(1, t.length());
		if (st.hasMoreElements())
			s += "E" + st.nextToken();
		return s;
	}
}
