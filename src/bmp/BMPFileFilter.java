package bmp;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class BMPFileFilter extends FileFilter {
    public String getDescription() {
        return "Bit Map Images";
    }
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().endsWith(".bmp") ||f.getName().endsWith(".BMP");
    }
}
