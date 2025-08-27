package control;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import javax.swing.JMenu;
import javax.swing.JPanel;

import bmp.BMPFile;
import bmp.BMPFileFilter;
import png.PngFileFilter;

@SuppressWarnings("serial")
public class Canvas extends JPanel implements Printable {
	private Control control;
	private OffImage offImage;
	private Parameter param;
	private JPopupMenu popup;
	private int popupX, popupY;
	private Timer drawTimer;

	public Canvas() {
		popup = new JPopupMenu();
		// popup.setLabel("Redraw");
		ExpandAtCenter eListener = new ExpandAtCenter();
		JMenu expandMenu = new JMenu("Expand at the Center");
		expandMenu.add("0.1").addActionListener(eListener);
		expandMenu.add("0.2").addActionListener(eListener);
		expandMenu.add("0.5").addActionListener(eListener);
		expandMenu.add("2").addActionListener(eListener);
		expandMenu.add("5").addActionListener(eListener);
		expandMenu.add("10").addActionListener(eListener);
		popup.add(expandMenu);

		popup.add("Translate This Point to the Center").addActionListener(new TranslateToCenter());

		TranslateExpandAtCenter ceListener = new TranslateExpandAtCenter();
		JMenu centerExpandMenu = new JMenu("Translate this Point to the Center and Expand");
		centerExpandMenu.add("0.1").addActionListener(ceListener);
		centerExpandMenu.add("0.2").addActionListener(ceListener);
		centerExpandMenu.add("0.5").addActionListener(ceListener);
		centerExpandMenu.add("2").addActionListener(ceListener);
		centerExpandMenu.add("5").addActionListener(ceListener);
		centerExpandMenu.add("10").addActionListener(ceListener);
		popup.add(centerExpandMenu);

		ExpandAtPointer leListener = new ExpandAtPointer();
		JMenu localExpandMenu = new JMenu("Expand at This Point");
		localExpandMenu.add("0.1").addActionListener(leListener);
		localExpandMenu.add("0.2").addActionListener(leListener);
		localExpandMenu.add("0.5").addActionListener(leListener);
		localExpandMenu.add("2").addActionListener(leListener);
		localExpandMenu.add("5").addActionListener(leListener);
		localExpandMenu.add("10").addActionListener(leListener);
		popup.add(localExpandMenu);

		MouseAdapter ma = new FractalMouseAdapter();
		addMouseListener(ma);
		addMouseMotionListener(ma);
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public void draw(Parameter param) {
		if (drawTimer != null && drawTimer.isRunning())
			stopDrawing();
		if (this.param != null && param.changeColoring(this.param) && offImage.completed()) {
			this.param = param;
			offImage.changeColoring(param);
			repaint();
			return;
		}
		this.param = param;
		offImage = new OffImage(param);
		setPreferredSize(new Dimension(param.viewX, param.viewY));
		revalidate();

		drawTimer = new Timer(1000, new ActionListener() {
			long startTime = System.currentTimeMillis();

			@Override
			public void actionPerformed(ActionEvent e) {
				control.topPanel.setTime((System.currentTimeMillis() - startTime) / 1000.0);
				repaint();
			}
		});
		drawTimer.start();
		new Thread() {
			@Override
			public void run() {
//				offScreen.buildAll();
//				offScreen.buildByPart();
				offImage.buildByLine();
				drawTimer.stop();
				repaint();
			};
		}.start();
	}

	public void stopDrawing() {
		offImage.stopBuilding();
		drawTimer.stop();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(offImage, 0, 0, this);
		/*
		 * if (mouseX < offImage.getWidth() && mouseY < offImage.getHeight()) {
		 * g.drawLine(mouseX, 0, mouseX, offImage.getHeight()); g.drawLine(0, mouseY,
		 * offImage.getWidth(), mouseY); }
		 */
	}

	@Override
	public int print(Graphics g, PageFormat pf, int pi) {
		if (pi == 0) {
			int ix = (int) (pf.getImageableX());
			int iy = (int) (pf.getImageableY());
			int w = (int) (pf.getImageableWidth());
			int h = (int) (pf.getImageableHeight());
			int cx = offImage.getWidth() / 2 - (int) (w / 2);
			int cy = offImage.getHeight() / 2 - (int) (h / 2);
			g.drawImage(offImage, ix, iy, ix + w - 1, iy + h - 1, cx, cy, cx + w - 1, cy + h - 1, this);
			return Printable.PAGE_EXISTS;
			// return Printable.NO_SUCH_PAGE;
		} else
			return Printable.NO_SUCH_PAGE;
	}

	// old version
	public void toBMPFile_old_not_used() {
		try {
			BMPFile bmpFile = new BMPFile(offImage);
			File bin = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			JFileChooser chooser = new JFileChooser(bin.getParent());
			chooser.addChoosableFileFilter(new BMPFileFilter());
			if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
				bmpFile.write(chooser.getSelectedFile().getPath());
		} catch (IOException | URISyntaxException e) {
		}
	}

	// new version
	public void toPngFile() {
		try {
			File bin = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			JFileChooser chooser = new JFileChooser(bin.getParent());
			chooser.addChoosableFileFilter(new PngFileFilter());
			if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				String path = chooser.getSelectedFile().getPath().trim();
				ImageIO.write(offImage, "png", new File(path + (path.toLowerCase().endsWith(".png") ? "" : ".png")));
			}
		} catch (IOException | URISyntaxException e) {
		}
	}

	private class FractalMouseAdapter extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger()) {
				popupX = e.getX();
				popupY = e.getY();
				popup.show(e.getComponent(), popupX, popupY);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
				control.leftPanel.centerTo(param.imageX2Space(e.getX()), param.imageY2Space(e.getY()));
				control.draw();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) {
				popupX = e.getX();
				popupY = e.getY();
				popup.show(e.getComponent(), popupX, popupY);
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			int mouseX = e.getX();
			int mouseY = e.getY();
			try {
				control.topPanel.describePointer(param.imageX2Space(mouseX), param.imageY2Space(mouseY),
						offImage.getRGB(mouseX, mouseY), offImage.getEscape(mouseX, mouseY));
			} catch (ArrayIndexOutOfBoundsException e1) {
			}
		}
	}

	private class TranslateExpandAtCenter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			control.leftPanel.centerTo(param.imageX2Space(popupX), param.imageY2Space(popupY));
			double factor = Double.parseDouble(e.getActionCommand());
			control.leftPanel.expandAtCenter(factor, factor);
			control.draw();
		}
	}

	private class ExpandAtPointer implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			double x = param.imageX2Space(popupX);
			double y = param.imageY2Space(popupY);
			double factor = Double.parseDouble(e.getActionCommand());
			control.leftPanel.expandAtPointer(x, y, factor, factor);
			control.draw();
		}
	}

	private class ExpandAtCenter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			double factor = Double.parseDouble(e.getActionCommand());
			control.leftPanel.expandAtCenter(factor, factor);
			control.draw();
		}
	}

	private class TranslateToCenter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			control.leftPanel.centerTo(param.imageX2Space(popupX), param.imageY2Space(popupY));
			control.draw();
		}
	}
}
