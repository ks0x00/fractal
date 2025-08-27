package control;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import complex.Complex;
import function.RationalPolynomial;

@SuppressWarnings("serial")
public class TopPanel extends JPanel implements ActionListener, Constants {
	private JTextField f, g, h;
	private JTextField thresh;
	private JTextField coordX, coordY;
	private JTextField color;
	private JTextField escape;
	private JTextField time;
	private Control control;

	public TopPanel() {
		super(new FlowLayout(FlowLayout.LEFT));
		add(new JLabel(" F"));
		f = new JTextField(10);
		f.addActionListener(this);
		add(f);
		add(new JLabel(" G"));
		g = new JTextField(10);
		g.addActionListener(this);
		add(g);
		add(new JLabel(" H"));
		h = new JTextField(10);
		h.addActionListener(this);
		add(h);
		add(new JLabel(" Thresh"));
		thresh = new JTextField(9);
		add(thresh);
		thresh.addActionListener(this);
		add(new JLabel(" X"));
		coordX = new JTextField(13);
		coordX.setEditable(false);
		add(coordX);
		add(new JLabel(" Y"));
		coordY = new JTextField(13);
		coordY.setEditable(false);
		add(coordY);
		add(new JLabel(" Color"));
		color = new JTextField(8);
		color.setEditable(false);
		add(color);
		add(new JLabel(" Iteration"));
		escape = new JTextField(8);
		escape.setEditable(false);
		add(escape);
		add(new JLabel(" Time"));
		time = new JTextField(5);
		time.setEditable(false);
		time.setHorizontalAlignment(JTextField.RIGHT);
		add(time);
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public void read(Parameter param) {
		param.f = RationalPolynomial.parseRationalPolynomial(f.getText(), 'z').simplifiedFunction();
		param.g = RationalPolynomial.parseRationalPolynomial(g.getText(), 'z').simplifiedFunction();
		param.h = param.type == MANDELBROT ? RationalPolynomial.parseRationalPolynomial(h.getText(), 'z').simplifiedFunction()
				: param.type == NEWTON ? param.f.diff() : null;
		double th = Double.parseDouble(thresh.getText());
		if (param.type == NEWTON)
			param.thresh = th;
		else
			param.thresh = th * th;
	}

	private String hexString(int i) {
		return String.format("%02X", i);
	}

	public void describePointer(double x, double y, int rgb, int escape) {
		coordX.setText(Complex.truncatedString(x));
		coordY.setText(Complex.truncatedString(y));
		color.setText(hexString((rgb >> 24) & 0xFF) + " " + hexString((rgb >> 16) & 0xFF) + " " + //
				hexString((rgb >> 8) & 0xFF) + " " + hexString(rgb & 0xFF));
		this.escape.setText(Integer.toString(escape));
	}

	public void setTime(double elapsedTime) {
		time.setText(String.format("%1.1f ", elapsedTime));
	}

	public void setDefaults(int type) {
		setFieldsEnabled(type);
		if (type == MANDELBROT) {
			f.setText("z^2");
			thresh.setText("2");
		} else if (type == JULIA) {
			f.setText("z^2-0.75+0.1234i");
			thresh.setText("2");
		} else {
			f.setText("z^3-1");
			thresh.setText("0.000000000001");
		}
		g.setText("z");
		h.setText("z");
	}

	public void setFieldsEnabled(int type) {
		if (type == MANDELBROT) {
			h.setEnabled(true);
//			thresh.setEnabled(true);
		} else if (type == JULIA) {
			h.setEnabled(false);
//			thresh.setEnabled(true);
		} else {
			h.setEnabled(false);
//			thresh.setEnabled(false);
		}
	}

	public void fixFunction(Parameter param) {
		if (f.getText().trim().equals(""))
			f.setText(param.type == MANDELBROT ? "z^2" : param.type == JULIA ? "z^2-0.75+0.1234i" : "z^3-1");
		if (g.getText().trim().equals(""))
			g.setText("z");
		if (h.getText().trim().equals(""))
			h.setText("z");
		if (thresh.getText().trim().equals(""))
			thresh.setText("2");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		control.draw();
	}

	// @formatter:off
	public String dataToSave() {
		return new StringBuilder()
				.append(f.getText()).append(';')
				.append(g.getText()).append(';')
				.append(h.getText()).append(';')
				.append(thresh.getText()).append(';')
				.toString();
	}
	// @formatter:on

	public int loadData(String data, int pos) {
		JTextField[] tfs = { f, g, h, thresh };
		for (JTextField tf : tfs) {
			int sep = data.indexOf(';', pos);
			tf.setText(data.substring(pos, sep));
			pos = sep + 1;
		}
		return pos;
	}
}
