package fractal;

import complex.Complex;
import control.Parameter;

public class Julia implements Fractal {
	private Parameter param;

	public Julia(Parameter param) {
		this.param = param;
	}

	@Override
	public int countEscape(Complex c) {
		Complex z = param.g.eval(c);
		int i = 0;
		// while(++i < param.iter && z.isBoundedBy(-2,-2, 2,2))
		while (z.sqrNorm() <= param.thresh && i++ < param.iter)
			z = param.f.eval(z);
		return i;
	}

}
