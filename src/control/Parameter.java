package control;

import function.Function;

public class Parameter implements Constants {
	public double minX, maxX;
	public double minY, maxY;
	public int viewX, viewY;
	public double unitX, unitY;
	public int iter;
	public int numThread;

	public int type;
	public String coloring;

	public Function f, g, h;
	public double thresh;

	public void setType(int type, Function f, Function g, Function h) {
		this.f = f;
		this.g = g;
		if (type == NEWTON)
			this.h = f.diff();
		else if (type == MANDELBROT)
			this.h = h;
		else
			this.h = null;
		this.type = type;
	}

	public void setColoring(String coloring) {
		this.coloring = coloring;
	}

	public void computeView() {
		viewX = (int) Math.round((maxX - minX) * unitX);
		viewY = (int) Math.round((maxY - minY) * unitY);
	}

	public double imageX2Space(int x) {
		return minX + x / unitX;
	}

	public double imageY2Space(int y) {
		return maxY - y / unitY;
	}

	public void centerTo(int imageX, int imageY) {
		centerTo(imageX2Space(imageX), imageY2Space(imageY));
	}

	public void centerTo(double spaceX, double spaceY) {
		centerXTo(spaceX);
		centerYTo(spaceY);
	}

	public void centerXTo(int imageX) {
		centerXTo(imageX2Space(imageX));
	}

	public void centerYTo(int imageY) {
		centerYTo(imageY2Space(imageY));
	}

	public void centerXTo(double spaceX) {
		double s = spaceX - (minX + maxX);
		minX += s;
		maxX += s;
	}

	public void centerYTo(double spaceY) {
		double s = spaceY - (minY + maxY) / 2;
		minY += s;
		maxY += s;
	}

	public void expandAtCenter(double factor) {
		expandXAtCenter(factor);
		expandYAtCenter(factor);
	}

	public void expandAtCenter(double factorX, double factorY) {
		expandXAtCenter(factorX);
		expandYAtCenter(factorY);
	}

	public void expandXAtCenter(double factorX) {
		unitX *= factorX;
		double t = minX;
		minX = ((factorX + 1) * t + (factorX - 1) * maxX) / (2 * factorX);
		maxX = ((factorX - 1) * t + (factorX + 1) * maxX) / (2 * factorX);
	}

	public void expandYAtCenter(double factorY) {
		unitY *= factorY;
		double t = minY;
		minY = ((factorY + 1) * t + (factorY - 1) * maxY) / (2 * factorY);
		maxY = ((factorY - 1) * t + (factorY + 1) * maxY) / (2 * factorY);
	}

	public boolean changeColoring(Parameter p) {
		return minX == p.minX && maxX == p.maxX && minY == p.minY && maxY == p.maxY && unitX == p.unitX && unitY == p.unitY
				&& iter == p.iter && numThread == p.numThread && type == p.type && f.equals(p.f) && g.equals(p.g)
				&& ((h == null && p.h == null) || h.equals(p.h)) && thresh == p.thresh;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Parameter) {
			Parameter p = (Parameter) obj;
			return minX == p.minX && maxX == p.maxX && minY == p.minY && maxY == p.maxY && unitX == p.unitX && unitY == p.unitY
					&& iter == p.iter && numThread == p.numThread && coloring == p.coloring && type == p.type && f.equals(p.f)
					&& g.equals(p.g) && h.equals(p.h) && thresh == p.thresh;
		}
		return false;
	}
}
