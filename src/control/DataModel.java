package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Vector;

import javax.swing.AbstractListModel;

@SuppressWarnings("serial")
public class DataModel extends AbstractListModel<String> implements Constants {
	private Vector<String> data;
	private boolean revised;

	public DataModel() {
		data = new Vector<>();
		try {
			File bin = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			BufferedReader br = new BufferedReader(new FileReader(new File(bin.getParent(), FILE_TO_SAVE)));
			String line;
			while ((line = br.readLine()) != null)
				data.add(line);
			br.close();
		} catch (IOException | URISyntaxException e) {
			// System.out.println("load default");
			// e.printStackTrace();
			String th = Integer.toString(8 * Runtime.getRuntime().availableProcessors());
			data.add("Mandelbrot Set;-2.1;1.1;-1.5;1.5;100;100;1000;" + th + ";RGB0;z^2;1/z;1/z;2;");
			data.add("Julia Set;-2;2;-2;2;100;100;10000;" + th + ";RGB0;z^2-0.75+0.1234i;z;z;2;");
			data.add("Newton Method;-2;2;-2;2;100;100;100;" + th + ";RGB0;z^3-1;z;z;0.000000000001;");
		}
		revised = false;
	}

	public boolean moveUp(int index) {
		if (0 < index && index < data.size()) {
			data.add(index - 1, data.remove(index));
			revised = true;
			return true;
		}
		return false;
	}

	public boolean moveDown(int index) {
		if (0 <= index && index < data.size() - 1) {
			data.add(index + 1, data.remove(index));
			revised = true;
			return true;
		}
		return false;
	}

	public boolean remove(int index) {
		if (0 <= index && index < data.size()) {
			data.remove(index);
			revised = true;
			return true;
		}
		return false;
	}

	public boolean add(String s) {
		for (String t : data)
			if (s.equals(t))
				return false;
		data.add(s);
		revised = true;
		return true;
	}

	@Override
	public String getElementAt(int index) {
		return data.elementAt(index);
	}

	@Override
	public int getSize() {
		return data.size();
	}

	public void writeToFile() {
		if (revised) {
			try {
				File bin = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
//			 System.out.println(bin.getAbsolutePath());
				FileWriter fw = new FileWriter(new File(bin.getParent(), FILE_TO_SAVE));
				for (int i = 0; i < data.size(); i++)
					fw.write(data.elementAt(i) + "\n");
				fw.close();
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
}
