package control;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class LeftPanel extends JPanel implements ActionListener, Constants {
	private JRadioButton mandelbrot, julia, newton;
	private JTextField minX, maxX, minY, maxY, unitX, unitY;
	private JTextField viewX, viewY;
	private JTextField iter;
	private JTextField numThread;
	private JButton[] mulUnits, mulIters, mulExpands;
	private JRadioButton[] rbColor;
	private JButton draw, stop, reset, print, png, load, save, fromText, toText;
	private Control control;

	public LeftPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		Box b = Box.createVerticalBox();
		ButtonGroup bgFractal = new ButtonGroup();

		mandelbrot = new JRadioButton("Mandelbrot Set", true);
		mandelbrot.setMargin(new Insets(-2, 0, 0, 0));
		bgFractal.add(mandelbrot);
		mandelbrot.addActionListener(this);
		b.add(mandelbrot);

		julia = new JRadioButton("Julia Set");
		julia.setMargin(new Insets(-2, 0, 0, 0));
		bgFractal.add(julia);
		julia.addActionListener(this);
		b.add(julia);

		newton = new JRadioButton("Newton Method");
		newton.setMargin(new Insets(-2, 0, 0, 0));
		bgFractal.add(newton);
		newton.addActionListener(this);
		b.add(newton);

		b.setAlignmentX(0.5f);
		panel.add(b);

		panel.add(Box.createVerticalStrut(10));
		JPanel p = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets.bottom = 5;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JPanel q = new JPanel(new GridLayout(2, 1));
		DocumentListener dlX = new TextListener() {
			@Override
			public void update() {
				try {
					setViewX();
				} catch (NumberFormatException e) {
				}
			}
		};
		minX = new JTextField(13);
		minX.setMargin(new Insets(-1, 0, -1, 0));
		minX.getDocument().addDocumentListener(dlX);
		minX.addActionListener(this);
		q.add(minX);
		maxX = new JTextField(13);
		maxX.setMargin(new Insets(-1, 0, -1, 0));
		maxX.getDocument().addDocumentListener(dlX);
		maxX.addActionListener(this);
		q.add(maxX);
		addLabel(p, "Range X", q, gbc);

		q = new JPanel(new GridLayout(2, 1));
		DocumentListener dlY = new TextListener() {
			@Override
			public void update() {
				try {
					setViewY();
				} catch (NumberFormatException e) {
				}
			}
		};
		minY = new JTextField(13);
		minY.setMargin(new Insets(-1, 0, -1, 0));
		minY.getDocument().addDocumentListener(dlY);
		minY.addActionListener(this);
		q.add(minY);
		maxY = new JTextField(13);
		maxY.setMargin(new Insets(-1, 0, -1, 0));
		maxY.getDocument().addDocumentListener(dlY);
		maxY.addActionListener(this);
		q.add(maxY);
		addLabel(p, "Range Y", q, gbc);

		q = new JPanel(new GridLayout(2, 1));
		viewX = new JTextField();
		viewX.setMargin(new Insets(-1, 0, -1, 0));
		viewX.setEnabled(false);
		q.add(viewX);
		viewY = new JTextField();
		viewY.setMargin(new Insets(-1, 0, -1, 0));
		viewY.setEnabled(false);
		q.add(viewY);
		addLabel(p, "View", q, gbc);

		q = new JPanel(new GridLayout(2, 1));
		unitX = new JTextField();
		unitX.setMargin(new Insets(-1, 0, -1, 0));
		unitX.getDocument().addDocumentListener(dlX);
		unitX.addActionListener(this);
		q.add(unitX);
		unitY = new JTextField();
		unitY.setMargin(new Insets(-1, 0, -1, 0));
		unitY.getDocument().addDocumentListener(dlY);
		unitY.addActionListener(this);
		q.add(unitY);
		gbc.insets.bottom = -3;
		addLabel(p, "Unit", q, gbc);

		q = new JPanel();
		mulUnits = new JButton[] { new JButton("x2"), new JButton("/ 2"), new JButton("x3"), new JButton("/ 3") };
		for (JButton jb : mulUnits) {
			jb.addActionListener(this);
			jb.setMargin(new Insets(-2, 2, -2, 2));
			q.add(jb);
		}
		gbc.insets.bottom = 5;
		p.add(q, gbc);

		iter = new JTextField();
		iter.setMargin(new Insets(-1, 0, -1, 0));
		iter.addActionListener(this);
		gbc.insets.bottom = -3;
		addLabel(p, "Iteration", iter, gbc);

		q = new JPanel();
		mulIters = new JButton[] { new JButton("x2"), new JButton("/ 2"), new JButton("x5"), new JButton("/ 5"), new JButton("x10"),
				new JButton("/ 10") };
		for (JButton jb : mulIters) {
			jb.addActionListener(this);
			jb.setMargin(new Insets(-2, 2, -2, 2));
			q.add(jb);
		}
		gbc.insets.bottom = 5;
		p.add(q, gbc);

		gbc.insets.bottom = -3;
		gbc.insets.left = 0;
		p.add(new JLabel("Expand At Center", JLabel.LEFT), gbc);
		q = new JPanel();
		mulExpands = new JButton[] { new JButton("x2"), new JButton("/ 2"), new JButton("x5"), new JButton("/ 5"),
				new JButton("x10"), new JButton("/ 10") };
		for (JButton jb : mulExpands) {
			jb.addActionListener(this);
			jb.setMargin(new Insets(-2, 2, -2, 2));
			q.add(jb);
		}
		p.add(q, gbc);
		panel.add(p);

		panel.add(Box.createVerticalStrut(10));
		int nCols = 5;
		int nRows = (COLOR_MODELS.length - 1) / nCols + 1;		
		KeyListener keyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String label = ((JRadioButton)e.getSource()).getText().toLowerCase();
				int index = 0;
				while (!label.equals(COLOR_MODELS[index]))
					index++;
					if (e.getKeyCode() == KeyEvent.VK_UP)
					index -= nCols;
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)
					index += nCols;
				else
					return;
				index = (COLOR_MODELS.length + index) % COLOR_MODELS.length;
				rbColor[index].requestFocus();
				rbColor[index].doClick();
			}
		};

		p = new JPanel(new GridLayout(nRows, nCols));
		rbColor = new JRadioButton[COLOR_MODELS.length];
		ButtonGroup bgColor = new ButtonGroup();
		for (int i = 0; i < COLOR_MODELS.length; i++) {
			rbColor[i] = new JRadioButton(COLOR_MODELS[i].toUpperCase());
			rbColor[i].setMargin(new Insets(-1, 0, -1, 0));
//			rbColor[i].setFont(rbColor[i].getFont().deriveFont(9.0f));
			InputMap im = rbColor[i].getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			im.put(KeyStroke.getKeyStroke("UP"), "none");
			im.put(KeyStroke.getKeyStroke("DOWN"), "none");
			rbColor[i].addKeyListener(keyListener);
			rbColor[i].addActionListener(this);
			bgColor.add(rbColor[i]);
			p.add(rbColor[i]);
		}
		rbColor[0].setSelected(true);
		panel.add(p);
		
		panel.add(Box.createVerticalStrut(10));
		p = new JPanel(new GridBagLayout());
		numThread = new JTextField();
		numThread.setMargin(new Insets(-1, 0, -1, 0));
		numThread.addActionListener(this);
//		gbc.insets.bottom = -3;
		addLabel(p, "Number of Threads", numThread, gbc);
		panel.add(p);

		panel.add(Box.createVerticalStrut(10));
		p = new JPanel(new GridLayout(5, 2));
		stop = new JButton("Stop");
		stop.setMargin(new Insets(-1, 2, -1, 2));
		stop.addActionListener(this);
		p.add(stop);
		draw = new JButton("Draw");
		draw.setMargin(new Insets(-1, 2, -1, 2));
		draw.addActionListener(this);
		p.add(draw);
		png = new JButton("PNG");
		png.setMargin(new Insets(-1, 2, -1, 2));
		png.addActionListener(this);
		p.add(png);
		print = new JButton("Print");
		print.setMargin(new Insets(-1, 2, -1, 2));
		print.addActionListener(this);
		p.add(print);
		load = new JButton("Load");
		load.setMargin(new Insets(-1, 2, -1, 2));
		load.addActionListener(this);
		p.add(load);
		save = new JButton("Save");
		save.setMargin(new Insets(-1, 2, -1, 2));
		save.addActionListener(this);
		p.add(save);
		fromText = new JButton("From Text");
		fromText.setMargin(new Insets(-1, 2, -1, 2));
		fromText.addActionListener(this);
		p.add(fromText);
		toText = new JButton("To Text");
		toText.setMargin(new Insets(-1, 2, -1, 2));
		toText.addActionListener(this);
		p.add(toText);
		reset = new JButton("Reset");
		reset.setMargin(new Insets(-1, 2, -1, 2));
		reset.addActionListener(this);
		p.add(reset);
		panel.add(p);

		add(panel);
	}

	private void addLabel(JPanel dest, String s, JComponent src, GridBagConstraints gbc) {
		gbc.weightx = 1.0;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbc.insets.left = 0;
		dest.add(new JLabel(s, SwingConstants.RIGHT), gbc);
		gbc.weightx = 9.0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets.left = 5;
		dest.add(src, gbc);
	}

	public void setDefaults() {
		if (mandelbrot.isSelected()) {
			minX.setText("-2.1");
			maxX.setText("1.1");
			minY.setText("-1.5");
			maxY.setText("1.5");
			unitX.setText("100");
			unitY.setText("100");
			iter.setText("1000");
		} else if (julia.isSelected()) {
			minX.setText("-2");
			maxX.setText("2");
			minY.setText("-2");
			maxY.setText("2");
			unitX.setText("100");
			unitY.setText("100");
			iter.setText("10000");
		} else {
			minX.setText("-2");
			maxX.setText("2");
			minY.setText("-2");
			maxY.setText("2");
			unitX.setText("100");
			unitY.setText("100");
			iter.setText("100");
		}
		numThread.setText(Integer.toString(Runtime.getRuntime().availableProcessors()));
		setView();
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public void setView() {
		setViewX();
		setViewY();
	}

	public void setViewX() {
		double x0 = Double.parseDouble(minX.getText());
		double x1 = Double.parseDouble(maxX.getText());
		double ux = Double.parseDouble(unitX.getText());
		viewX.setText(Long.toString(Math.round((x1 - x0) * ux)));
	}

	public void setViewY() {
		double y0 = Double.parseDouble(minY.getText());
		double y1 = Double.parseDouble(maxY.getText());
		double uy = Double.parseDouble(unitY.getText());
		viewY.setText(Long.toString(Math.round((y1 - y0) * uy)));
	}

	public int getType() {
		return mandelbrot.isSelected() ? MANDELBROT : julia.isSelected() ? JULIA : NEWTON;
	}

	public void setLoadEnabled(boolean enabled) {
		load.setEnabled(enabled);
	}

	public void read(Parameter param) {
		param.type = getType();
		param.minX = Double.parseDouble(minX.getText());
		param.maxX = Double.parseDouble(maxX.getText());
		param.minY = Double.parseDouble(minY.getText());
		param.maxY = Double.parseDouble(maxY.getText());
		param.viewX = Integer.parseInt(viewX.getText());
		param.viewY = Integer.parseInt(viewY.getText());
		param.unitX = Double.parseDouble(unitX.getText());
		param.unitY = Double.parseDouble(unitY.getText());
		param.iter = Integer.parseInt(iter.getText());
		param.numThread = Integer.parseInt(numThread.getText());
		for (JRadioButton rb : rbColor)
			if (rb.isSelected()) {
				param.coloring = rb.getText().toLowerCase();
				break;
			}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (JButton jb : mulUnits)
			if (e.getSource() == jb) {
				double ux = Double.parseDouble(unitX.getText());
				double uy = Double.parseDouble(unitY.getText());
				int exp = Integer.parseInt(jb.getText().substring(1).trim());
				if (jb.getText().charAt(0) == 'x') {
					unitX.setText(Double.toString(ux * exp));
					unitY.setText(Double.toString(uy * exp));
				} else {
					unitX.setText(Double.toString(ux / exp));
					unitY.setText(Double.toString(uy / exp));
				}
				control.draw();
				return;
			}
		for (JButton jb : mulIters)
			if (e.getSource() == jb) {
				int it = Integer.parseInt(iter.getText());
				int exp = Integer.parseInt(jb.getText().substring(1).trim());
				if (jb.getText().charAt(0) == 'x')
					iter.setText(Integer.toString(it * exp));
				else
					iter.setText(Integer.toString(it / exp));
				control.draw();
				return;
			}
		for (JButton jb : mulExpands)
			if (e.getSource() == jb) {
				int exp = Integer.parseInt(jb.getText().substring(1).trim());
				if (jb.getText().charAt(0) == 'x')
					expandAtCenter(exp, exp);
				else
					expandAtCenter(1.0 / exp, 1.0 / exp);
				control.draw();
				return;
			}
		if (e.getSource() == load)
			control.dataManager.showDialog();
		else if (e.getSource() == save)
			control.addDataToModel();
		else if (e.getSource() == fromText)
			control.drawDataFromDialog();
		else if (e.getSource() == toText)
			control.showDataToDialog();
		else if (e.getSource() == print)
			control.print();
		else if (e.getSource() == png)
			control.canvas.toPngFile();
		else if (e.getSource() == stop)
			control.canvas.stopDrawing();
		else {
			if (e.getSource() == reset || e.getSource() == newton || e.getSource() == mandelbrot || e.getSource() == julia)
				control.setDefaults();
			control.draw();
		}
	}

	public void centerTo(double x, double y) {
		double l = Double.parseDouble(minX.getText());
		double u = Double.parseDouble(maxX.getText());
		double t = x - (l + u) / 2;
		minX.setText(Double.toString(l + t));
		maxX.setText(Double.toString(u + t));

		l = Double.parseDouble(minY.getText());
		u = Double.parseDouble(maxY.getText());
		t = y - (l + u) / 2;
		minY.setText(Double.toString(l + t));
		maxY.setText(Double.toString(u + t));
	}

	public void expandAtCenter(double factorX, double factorY) {
		unitX.setText(Double.toString(factorX * Double.parseDouble(unitX.getText())));
		double l = Double.parseDouble(minX.getText());
		double u = Double.parseDouble(maxX.getText());
		minX.setText(Double.toString(((factorX + 1) * l + (factorX - 1) * u) / (2 * factorX)));
		maxX.setText(Double.toString(((factorX - 1) * l + (factorX + 1) * u) / (2 * factorX)));

		unitY.setText(Double.toString(factorY * Double.parseDouble(unitY.getText())));
		l = Double.parseDouble(minY.getText());
		u = Double.parseDouble(maxY.getText());
		minY.setText(Double.toString(((factorY + 1) * l + (factorY - 1) * u) / (2 * factorY)));
		maxY.setText(Double.toString(((factorY - 1) * l + (factorY + 1) * u) / (2 * factorY)));
	}

	public void expandAtPointer(double x, double y, double factorX, double factorY) {
		unitX.setText(Double.toString(factorX * Double.parseDouble(unitX.getText())));
		double l = Double.parseDouble(minX.getText());
		double u = Double.parseDouble(maxX.getText());
		minX.setText(Double.toString(x + (l - x) / factorX));
		maxX.setText(Double.toString(x + (u - x) / factorX));

		unitY.setText(Double.toString(factorY * Double.parseDouble(unitY.getText())));
		l = Double.parseDouble(minY.getText());
		u = Double.parseDouble(maxY.getText());
		minY.setText(Double.toString(y + (l - y) / factorY));
		maxY.setText(Double.toString(y + (u - y) / factorY));
	}

	// @formatter:off
	public String dataToSave() {
		String frac = mandelbrot.isSelected() ? mandelbrot.getText() : julia.isSelected() ? julia.getText() : newton.getText();
		int rb = -1;
		while (!rbColor[++rb].isSelected())
			;
		return new StringBuilder()
				.append(frac).append(';')
				.append(minX.getText()).append(';')
				.append(maxX.getText()).append(';')
				.append(minY.getText()).append(';')
				.append(maxY.getText()).append(';')
				.append(unitX.getText()).append(';')
				.append(unitX.getText()).append(';')
				.append(iter.getText()).append(';')
				.append(numThread.getText()).append(';')
				.append(rbColor[rb].getText()).append(';')
				.toString();
	}
	// @formatter:on

	public int loadData(String data, int pos) {
		int sep = data.indexOf(';', pos);
		String type = data.substring(pos, sep);
		JRadioButton[] rbs = { mandelbrot, julia, newton };
		for (JRadioButton rb : rbs)
			if (rb.getText().equals(type)) {
				rb.setSelected(true);
				break;
			}
		pos = sep + 1;

		JTextField[] tfs = { minX, maxX, minY, maxY, unitX, unitY, iter, numThread };
		for (JTextField tf : tfs) {
			sep = data.indexOf(';', pos);
			tf.setText(data.substring(pos, sep));
			pos = sep + 1;
		}

		sep = data.indexOf(';', pos);
		type = data.substring(pos, sep);
		for (JRadioButton rb : rbColor)
			if (rb.getText().equals(type)) {
				rb.setSelected(true);
				break;
			}
		return sep + 1;
	}
}
