package control;

import java.awt.image.BufferedImage;

import complex.Complex;
import fractal.ColorTable;
import fractal.Fractal;
import fractal.Julia;
import fractal.Mandelbrot;
import fractal.Newton;

public class OffImage extends BufferedImage implements Constants {
	private Parameter param;
	private int[] escapes;
	private boolean building;
	private Fractal fractal;
	private int[] colors;
	private double pixelX, pixelY;
	private double[] coordsX;

	public OffImage(Parameter param) {
		super(param.viewX, param.viewY, BufferedImage.TYPE_INT_RGB);
		this.param = param;
		getGraphics().fillRect(0, 0, param.viewX, param.viewY);

		building = false;
		escapes = new int[param.viewX * param.viewY];
		fractal = param.type == MANDELBROT ? new Mandelbrot(param) : param.type == JULIA ? new Julia(param) : new Newton(param);
		colors = ColorTable.getTable(param);
		pixelX = (param.maxX - param.minX) / param.viewX;
		pixelY = (param.maxY - param.minY) / param.viewY;
		coordsX = new double[param.viewX];
		for (int i = 0; i < param.viewX; i++)
			coordsX[i] = param.minX + i * pixelX;
	}

	public int getEscape(int i, int j) {
		return escapes[j * param.viewX + i];
	}

	public void buildAll() {
		int n = 0;
		Complex c = new Complex();
		building = true;
		for (int j = 0; j < param.viewY; j++) {
			if (!building)
				return;
			c.setY(param.maxY - j * pixelY);
			for (int i = 0; i < param.viewX; i++) {
				c.setX(coordsX[i]);
				escapes[n] = fractal.countEscape(c);
				setRGB(i, j, colors[escapes[n]]);
				n++;
			}
		}
	}

	public void buildByPart() {
		float step = param.viewY / (float) param.numThread;
		Thread[] th = new Thread[param.numThread];
		for (int i = 0; i < param.numThread; i++) {
			int start = Math.round(i * step);
			int end = Math.round((i + 1) * step);
			th[i] = new Thread() {
				@Override
				public void run() {
					buildPart(start, end);
				}
			};
			th[i].start();
		}
		for (int i = 0; i < param.numThread; i++)
			try {
				th[i].join();
			} catch (InterruptedException e) {
			}
	}

	public void buildPart(int start, int end) {
		int n = start * param.viewX;
		Complex c = new Complex();
		building = true;
		for (int j = start; j < end; j++) {
			if (!building)
				return;
			c.setY(param.maxY - j * pixelY);
			for (int i = 0; i < param.viewX; i++) {
				c.setX(coordsX[i]);
				escapes[n] = fractal.countEscape(c);
				setRGB(i, j, colors[escapes[n++]]);
			}
		}
	}

	public void buildByLine() {
		building = true;
		Thread[] th = new Thread[param.numThread];
		int[] row = { 0 };
		for (int i = 0; i < param.numThread; i++) {
			th[i] = new Thread() {
				@Override
				public void run() {
					while (row[0] < param.viewY && building)
						buildLine(row[0]++);
				}
			};
			th[i].start();
		}
		for (int i = 0; i < param.numThread; i++)
			try {
				th[i].join();
			} catch (InterruptedException e) {
			}
	}

	public void buildLine(int line) {
		int n = line * param.viewX;
		Complex c = new Complex();
		c.setY(param.maxY - line * pixelY);
		for (int i = 0; i < param.viewX; i++) {
			c.setX(coordsX[i]);
			escapes[n] = fractal.countEscape(c);
			setRGB(i, line, colors[escapes[n++]]);
		}
	}

	public void stopBuilding() {
		building = false;
	}

	public void changeColoring(Parameter param) {
		this.param = param;
		colors = ColorTable.getTable(param);
		int n = 0;
		for (int j = 0; j < param.viewY; j++) {
			for (int i = 0; i < param.viewX; i++) {
				setRGB(i, j, colors[escapes[n++]]);
			}
		}
	}

	public boolean completed() {
		return building;
	}
}
