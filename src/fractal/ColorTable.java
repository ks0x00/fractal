package fractal;

import java.awt.Color;
import java.lang.reflect.Method;

import control.Parameter;

public class ColorTable {
	public static int[] getTable(Parameter param) {
		try {
			Method m = ColorTable.class.getMethod(param.coloring, int.class);
			return (int[]) m.invoke(ColorTable.class, param.iter);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int[] bgr0(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++)
			table[i] = (int) Math.round(i * colorUnit);
		return table;
	}

	public static int[] bgr1(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++)
			table[i] = Bit.exchange4((int) Math.round(i * colorUnit));
		return table;
	}

	public static int[] bgr2(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++)
			table[i] = Bit.rev24Bit((int) Math.round(i * colorUnit));
		return table;
	}

	public static int[] bgr3(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.revExchange4((int) Math.round(i * colorUnit));
			int r = (int) (255 - 255 * Math.sqrt((bgr & 0xFF) / 255.0));
			int g = (int) (255 - 255 * Math.sqrt(((bgr >> 8) & 0xFF) / 255.0));
			int b = (int) (255 - 255 * Math.sqrt(((bgr >> 16) & 0xFF) / 255.0));
			table[i] = (b << 16) | (g << 8) | r;
		}
		return table;
	}

	public static int[] bgr4(int iter) {
		int[] table = new int[iter + 2];
		for (int i = 0; i <= iter + 1; i++)
			table[i] = ((i & 0xFF) << 16) | (i & 0xFF00) | ((i >> 16) & 0xFF);
		return table;
	}

	public static int[] grb0(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = (int) Math.round(i * colorUnit);
			table[i] = ((bgr & 0xFFFF) << 8) | ((bgr >> 16) & 0xFF);
		}
		return table;
	}

	public static int[] grb1(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.exchange4((int) Math.round(i * colorUnit));
			table[i] = ((bgr & 0xFFFF) << 8) | ((bgr >> 16) & 0xFF);
		}
		return table;
	}

	public static int[] grb2(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.rev24Bit((int) Math.round(i * colorUnit));
			table[i] = ((bgr & 0xFFFF) << 8) | ((bgr >> 16) & 0xFF);
		}
		return table;
	}

	public static int[] grb3(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.revExchange4((int) Math.round(i * colorUnit));
			int r = (int) (255 - 255 * Math.sqrt((bgr & 0xFF) / 255.0));
			int g = (int) (255 - 255 * Math.sqrt(((bgr >> 8) & 0xFF) / 255.0));
			int b = (int) (255 - 255 * Math.sqrt(((bgr >> 16) & 0xFF) / 255.0));
			table[i] = (g << 16) | (r << 8) | b;
		}
		return table;
	}

	public static int[] grb4(int iter) {
		int[] table = new int[iter + 2];
		for (int i = 0; i <= iter + 1; i++)
			table[i] = ((i & 0xFFFF) << 8) | ((i >> 16) & 0xFF);
		return table;
	}

	public static int[] rbg0(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = (int) Math.round(i * colorUnit);
			table[i] = ((bgr & 0xFF) << 16) | ((bgr >> 8) & 0xFFFF);
		}
		return table;
	}

	public static int[] rbg1(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.exchange4((int) Math.round(i * colorUnit));
			table[i] = ((bgr & 0xFF) << 16) | ((bgr >> 8) & 0xFFFF);
		}
		return table;
	}

	public static int[] rbg2(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.rev24Bit((int) Math.round(i * colorUnit));
			table[i] = ((bgr & 0xFF) << 16) | ((bgr >> 8) & 0xFFFF);
		}
		return table;
	}

	public static int[] rbg3(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.revExchange4((int) Math.round(i * colorUnit));
			int r = (int) (255 - 255 * Math.sqrt((bgr & 0xFF) / 255.0));
			int g = (int) (255 - 255 * Math.sqrt(((bgr >> 8) & 0xFF) / 255.0));
			int b = (int) (255 - 255 * Math.sqrt(((bgr >> 16) & 0xFF) / 255.0));
			table[i] = (r << 16) | (b << 8) | g;
		}
		return table;
	}

	public static int[] rbg4(int iter) {
		int[] table = new int[iter + 2];
		for (int i = 0; i <= iter + 1; i++)
			table[i] = ((i & 0xFF) << 16) | ((i >> 8) & 0xFFFF);
		return table;
	}

	public static int[] rgb0(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = (int) Math.round(i * colorUnit);
			table[i] = ((bgr & 0xFF) << 16) | (bgr & 0xFF00) | ((bgr >> 16) & 0xFF);
		}
		return table;
	}

	public static int[] rgb1(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.exchange4((int) Math.round(i * colorUnit));
			table[i] = ((bgr & 0xFF) << 16) | (bgr & 0xFF00) | ((bgr >> 16) & 0xFF);
		}
		return table;
	}

	public static int[] rgb2(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.rev24Bit((int) Math.round(i * colorUnit));
			table[i] = ((bgr & 0xFF) << 16) | (bgr & 0xFF00) | ((bgr >> 16) & 0xFF);
		}
		return table;
	}

	public static int[] rgb3(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.revExchange4((int) Math.round(i * colorUnit));
			int r = (int) (255 - 255 * Math.sqrt((bgr & 0xFF) / 255.0));
			int g = (int) (255 - 255 * Math.sqrt(((bgr >> 8) & 0xFF) / 255.0));
			int b = (int) (255 - 255 * Math.sqrt(((bgr >> 16) & 0xFF) / 255.0));
			table[i] = (r << 16) | (g << 8) | b;
		}
		return table;
	}

	public static int[] rgb4(int iter) {
		int[] table = new int[iter + 2];
		for (int i = 0; i <= iter + 1; i++)
			table[i] = ((i & 0xFF) << 16) | (i & 0xFF00) | ((i >> 16) & 0xFF);
		return table;
	}

	public static int[] gbr0(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = (int) Math.round(i * colorUnit);
			table[i] = ((bgr & 0xFF00) << 8) | ((bgr >> 8) & 0xFF00) | (bgr & 0xFF);
		}
		return table;
	}

	public static int[] gbr1(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.exchange4((int) Math.round(i * colorUnit));
			table[i] = ((bgr & 0xFF00) << 8) | ((bgr >> 8) & 0xFF00) | (bgr & 0xFF);
		}
		return table;
	}

	public static int[] gbr2(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.rev24Bit((int) Math.round(i * colorUnit));
			table[i] = ((bgr & 0xFF00) << 8) | ((bgr >> 8) & 0xFF00) | (bgr & 0xFF);
		}
		return table;
	}

	public static int[] gbr3(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.revExchange4((int) Math.round(i * colorUnit));
			int r = (int) (255 - 255 * Math.sqrt((bgr & 0xFF) / 255.0));
			int g = (int) (255 - 255 * Math.sqrt(((bgr >> 8) & 0xFF) / 255.0));
			int b = (int) (255 - 255 * Math.sqrt(((bgr >> 16) & 0xFF) / 255.0));
			table[i] = (g << 16) | (b << 8) | r;
		}
		return table;
	}

	public static int[] gbr4(int iter) {
		int[] table = new int[iter + 2];
		for (int i = 0; i <= iter + 1; i++)
			table[i] = ((i & 0xFF00) << 8) | ((i >> 8) & 0xFF00) | (i & 0xFF);
		return table;
	}

	public static int[] brg0(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = (int) Math.round(i * colorUnit);
			table[i] = (bgr & 0xFF0000) | ((bgr & 0xFF) << 8) | ((bgr >> 8) & 0xFF);
		}
		return table;
	}

	public static int[] brg1(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.exchange4((int) Math.round(i * colorUnit));
			table[i] = (bgr & 0xFF0000) | ((bgr & 0xFF) << 8) | ((bgr >> 8) & 0xFF);
		}
		return table;
	}

	public static int[] brg2(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.rev24Bit((int) Math.round(i * colorUnit));
			table[i] = (bgr & 0xFF0000) | ((bgr & 0xFF) << 8) | ((bgr >> 8) & 0xFF);
		}
		return table;
	}

	public static int[] brg3(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int bgr = Bit.revExchange4((int) Math.round(i * colorUnit));
			int r = (int) (255 - 255 * Math.sqrt((bgr & 0xFF) / 255.0));
			int g = (int) (255 - 255 * Math.sqrt(((bgr >> 8) & 0xFF) / 255.0));
			int b = (int) (255 - 255 * Math.sqrt(((bgr >> 16) & 0xFF) / 255.0));
			table[i] = (b << 16) | (g << 8) | r;
		}
		return table;
	}

	public static int[] brg4(int iter) {
		int[] table = new int[iter + 2];
		for (int i = 0; i <= iter + 1; i++)
			table[i] = (i & 0xFF0000) | ((i & 0xFF) << 8) | ((i >> 8) & 0xFF);
		return table;
	}

	public static int[] hsb0(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int hsb = (int) Math.round(i * colorUnit);
			table[i] = Color.HSBtoRGB((float) pol2((hsb >> 8) & 0xFF), (float) pol2(hsb & 0xFF), (float) pol2((hsb >> 16) & 0xFF));
		}
		return table;
	}

	public static int[] hsb1(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int hsb = Bit.exchange4((int) Math.round(i * colorUnit));
			table[i] = Color.HSBtoRGB((float) pol2((hsb >> 8) & 0xFF), (float) pol2(hsb & 0xFF), (float) pol2((hsb >> 16) & 0xFF));
		}
		return table;
	}

	public static int[] hsb2(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int hsb = Bit.rev24Bit((int) Math.round(i * colorUnit));
			table[i] = Color.HSBtoRGB((float) pol2((hsb >> 8) & 0xFF), (float) pol2(hsb & 0xFF), (float) pol2((hsb >> 16) & 0xFF));
		}
		return table;
	}

	public static int[] hsb3(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int hsb = Bit.revExchange4((int) Math.round(i * colorUnit));
			table[i] = Color.HSBtoRGB((float) pol2((hsb >> 8) & 0xFF), (float) pol2(hsb & 0xFF), (float) pol2((hsb >> 16) & 0xFF));
		}
		return table;
	}

	public static int[] hsb4(int iter) {
		int[] table = new int[iter + 2];
		for (int i = 0; i <= iter + 1; i++)
			table[i] = Color.HSBtoRGB((float) pol2((i >> 8) & 0xFF), (float) pol2(i & 0xFF), (float) pol2((i >> 16) & 0xFF));
		return table;
	}

	public static int[] hsl0(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int hsl = (int) Math.round(i * colorUnit);
			table[i] = hsl2Rgb(pol1((hsl >> 8) & 0xFF), pol1((hsl >> 16) & 0xFF), pol1(hsl & 0xFF));
		}
		return table;
	}

	public static int[] hsl1(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int hsl = Bit.exchange4((int) Math.round(i * colorUnit));
			table[i] = hsl2Rgb(pol1((hsl >> 8) & 0xFF), pol1((hsl >> 16) & 0xFF), pol1(hsl & 0xFF));
		}
		return table;
	}

	public static int[] hsl2(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int hsl = Bit.rev24Bit((int) Math.round(i * colorUnit));
			table[i] = hsl2Rgb(pol1((hsl >> 8) & 0xFF), pol1((hsl >> 16) & 0xFF), pol1(hsl & 0xFF));
		}
		return table;
	}

	public static int[] hsl3(int iter) {
		int[] table = new int[iter + 2];
		double colorUnit = 0xFFFFFF / (double) iter;
		for (int i = 0; i <= iter + 1; i++) {
			int hsl = Bit.revExchange4((int) Math.round(i * colorUnit));
			table[i] = hsl2Rgb(pol2((hsl >> 8) & 0xFF), pol2(hsl & 0xFF), pol2((hsl >> 16) & 0xFF));
		}
		return table;
	}

	public static int[] hsl4(int iter) {
		int[] table = new int[iter + 2];
		for (int i = 0; i <= iter + 1; i++)
			table[i] = hsl2Rgb(pol1((i >> 8) & 0xFF), pol1((i >> 16) & 0xFF), pol1(i & 0xFF));
		return table;
	}

	public static int hsl2Rgb(double h, double s, double l) {
		int r, g, b;
		if (s == 0) {
			r = g = b = (int) Math.round(255 * l); // achromatic
		} else {
			double q = l < 0.5 ? l * (1 + s) : l + s - l * s;
			double p = 2 * l - q;
			r = (int) Math.round(255 * hue2Rgb(p, q, h + 1.0 / 3));
			g = (int) Math.round(255 * hue2Rgb(p, q, h));
			b = (int) Math.round(255 * hue2Rgb(p, q, h - 1.0 / 3));
		}
		return (r << 16) | (g << 8) | b;
	}

	public static double hue2Rgb(double p, double q, double t) {
		if (t < 0)
			t += 1;
		else if (t > 1)
			t -= 1;
		if (t < 1.0 / 6)
			return p + (q - p) * 6 * t;
		if (t < 1.0 / 2)
			return q;
		if (t < 2.0 / 3)
			return p + (q - p) * (4 - 6 * t);
		return p;
	}

	public static double pol1(int x) {
		return 1 - x / 255.0;
	}

	public static double pol2(int x) {
		double a = 1 - x / 255.0;
		return a * a;
	}
}
