package bmp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BMPFile {
	private FileHeader fh;
	private InfoHeader ih;
	private int[][] table;

	public BMPFile(int[][] table) {
		int width = table[0].length;
		int height = table.length;

		fh = new FileHeader(4 * width * height + 56);
		ih = new InfoHeader(width, height);
		this.table = table;
	}

	public BMPFile(BufferedImage bi) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		table = new int[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				table[i][j] = bi.getRGB(j, i);
		int fitTerm = (width * 3) % 4;
		if (fitTerm != 0)
			fitTerm = 4 - fitTerm;
		fh = new FileHeader((3 * width + fitTerm) * height + 54);
		ih = new InfoHeader(width, height);
	}

	public BMPFile(String fileName) throws FileNotFoundException, IOException {
		this(new File(fileName));
	}

	public BMPFile(File f) throws FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(f);
		byte[] b = new byte[14];
		fis.read(b);
		fh = new FileHeader(b);
		b = new byte[40];
		fis.read(b);
		ih = new InfoHeader(b);
		int width = (int) ih.getWidth();
		int temp = (width * 3) % 4;
		if (temp != 0)
			temp = 4 - temp;
		b = new byte[width * 3 + temp];
		int height = (int) ih.getHeight();
		table = new int[height][width];
		for (int i = 0; i < height; i++) {
			fis.read(b);
			for (int j = 0; j < width; j++) {
				temp = 3 * j;
				table[i][j] = Utility.bytesToInt(b[temp], b[temp + 1], b[temp + 2]);
			}
		}
		fis.close();
	}

	public void write(String fileName) throws IOException {
		String s = fileName.trim();
		write(new File(s + (s.toLowerCase().endsWith(".bmp") ? "" : ".bmp")));
	}

	public void write(File f) throws IOException {
		FileOutputStream fos = new FileOutputStream(f);
		fh.appendTo(fos);
		ih.appendTo(fos);
		int width = table[0].length;
		int height = table.length;
		int rem = (width * 3) % 4;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++)
				fos.write(Utility.intToRGBBytes(table[i][j]));
			switch (rem) {
			case 1:
				fos.write(0);
			case 2:
				fos.write(0);
			case 3:
				fos.write(0);
			}
		}
		fos.close();
	}
}
