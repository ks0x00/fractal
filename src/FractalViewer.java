import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import control.Canvas;
import control.Control;
import control.LeftPanel;
import control.TopPanel;

public class FractalViewer {
	private static void createAndShowGUI() {
		JFrame f = new JFrame("Fractal Viewer");
		TopPanel topPanel = new TopPanel();
		LeftPanel leftPanel = new LeftPanel();
		Canvas canvas = new Canvas();
		Control control = new Control(f, topPanel, leftPanel, canvas);

		Container c = f.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(topPanel, BorderLayout.NORTH);
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(leftPanel), new JScrollPane(canvas));
//		sp.setDividerLocation(213);
		sp.setDividerLocation(274);
		c.add(sp);
		
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				try {
					control.writeDataToFile();
				} catch (Exception e) {
				}
				f.dispose();
				System.exit(0);
			}
		});
		
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		f.setSize(scrSize.width * 3 / 4, scrSize.height * 3 / 4);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
