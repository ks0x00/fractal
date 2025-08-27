package function;

import complex.Complex;

public interface Function {
	public Complex eval(Complex z);

	public Function diff();
}
