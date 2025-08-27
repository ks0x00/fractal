package fractal;

import complex.Complex;
import control.Parameter;

public class Newton implements Fractal {
	private Parameter param;

	public Newton(Parameter param) {
		this.param = param;
	}

	@Override
	public int countEscape(Complex c) {
		Complex z = param.g.eval(c);
		Complex n = param.f.eval(z);
		int i = 0;
		// while (n.sqrNorm() > 0.000000000001 && i++ < param.iter) {
		while (n.sqrNorm() > param.thresh && i++ < param.iter) {
			n.div(param.h.eval(z));
			z.sub(n);
			n = param.f.eval(z);
		}
		return i;
	}

}
