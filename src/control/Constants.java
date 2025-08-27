package control;

public interface Constants {
	int MANDELBROT = 0;
	int JULIA = 1;
	int NEWTON = 2;
	String[] COLOR_MODELS = {
			"bgr0", "bgr1", "bgr2", "bgr3", "bgr4", 
			"grb0", "grb1", "grb2", "grb3", "grb4", 
			"rbg0", "rbg1", "rbg2", "rbg3", "rbg4", 
			"rgb0", "rgb1", "rgb2", "rgb3", "rgb4", 
			"gbr0", "gbr1", "gbr2", "gbr3", "gbr4", 
			"brg0", "brg1", "brg2", "brg3", "brg4", 
			"hsb0", "hsb1", "hsb2", "hsb3", "hsb4", 
			"hsl0", "hsl1", "hsl2", "hsl3",	"hsl4" 
	};
	String FILE_TO_SAVE = "FractalsV9.dat";
}
