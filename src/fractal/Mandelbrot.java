package fractal;

import complex.Complex;
import control.Parameter;

public class Mandelbrot implements Fractal {
	private Parameter param;

	public Mandelbrot(Parameter param) {
		this.param = param;
	}

	@Override
	public int countEscape(Complex c) {
		int i = 0;
		Complex z = param.g.eval(c);
		Complex z0 = param.h.eval(c);
		// while(++i < param.iter && z.isBoundedBy(-2, -2, 2, 2))
		while (z.sqrNorm() <= param.thresh && i++ < param.iter) {
			z = param.f.eval(z);
			z.add(z0);
		}
		return i;
	}

}
