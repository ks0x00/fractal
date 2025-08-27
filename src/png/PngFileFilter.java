package png;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class PngFileFilter extends FileFilter {
	public String getDescription() {
		return "Portable Network Graphics(PNG) Images";
	}

	public boolean accept(File f) {
		return f.isDirectory() || f.getName().toLowerCase().endsWith(".png");
	}
}
