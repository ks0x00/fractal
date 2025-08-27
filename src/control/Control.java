package control;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.print.PrinterException;

public class Control {
	protected JFrame parent;
	protected TopPanel topPanel;
	protected LeftPanel leftPanel;
	protected Canvas canvas;
	protected DataManager dataManager;

	public Control(JFrame parent, TopPanel topPanel, LeftPanel leftPanel, Canvas canvas) {
		this.parent = parent;
		this.topPanel = topPanel;
		topPanel.setControl(this);
		this.leftPanel = leftPanel;
		leftPanel.setControl(this);
		this.canvas = canvas;
		canvas.setControl(this);

		dataManager = new DataManager(this);

		setDefaults();
		draw();
	}

	public void setDefaults() {
		leftPanel.setDefaults();
		topPanel.setDefaults(leftPanel.getType());
	}

	public void draw() {
		Parameter param = new Parameter();
		leftPanel.read(param);
		topPanel.read(param);
		canvas.draw(param);
	}

	public void writeDataToFile() {
		dataManager.writeToFile();
	}

	public void addDataToModel() {
		dataManager.addDataToDataModel(leftPanel.dataToSave() + topPanel.dataToSave());
	}

	public void drawData(String data) {
		int pos = leftPanel.loadData(data, 0);
		topPanel.setFieldsEnabled(leftPanel.getType());
		topPanel.loadData(data, pos);
		draw();
	}

	public void drawDataFromDialog() {
		JDialog dlg = new JDialog(parent, "Data", true);
		dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Container c = dlg.getContentPane();
		KeyListener keyListener = new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
					dlg.dispose();
			}
		};
		JTextField tf = new JTextField(40);
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Cancel"))
					dlg.dispose();
				else if (e.getActionCommand().equals("Draw")) {
					String str = tf.getText().trim();
					dlg.dispose();
					try {
						drawData(str);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		};
		tf.addActionListener(actionListener);
		tf.addKeyListener(keyListener);
		c.add(tf);
		JPanel p = new JPanel();
		JButton draw = new JButton("Draw");
		draw.addActionListener(actionListener);
		draw.setMargin(new Insets(0, 25, 0, 25));
		p.add(draw);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(actionListener);
		cancel.setPreferredSize(draw.getPreferredSize());
		p.add(cancel);
		c.add(p, BorderLayout.SOUTH);
		dlg.pack();
		dlg.setLocationRelativeTo(parent);
		dlg.setVisible(true);
	}

	public void showDataToDialog() {
		JDialog dlg = new JDialog(parent, "Data", true);
		dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Container c = dlg.getContentPane();
		KeyListener keyListener = new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
					dlg.dispose();
			}
		};
		JTextField tf = new JTextField(leftPanel.dataToSave() + topPanel.dataToSave());
		tf.addKeyListener(keyListener);
		c.add(tf);
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Exit"))
					dlg.dispose();
				else if (e.getActionCommand().equals("Copy to clipboard")){
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					StringSelection sel = new StringSelection(tf.getText());
					clipboard.setContents(sel, sel);
				}
			}
		};
		JPanel p = new JPanel();
		JButton clip = new JButton("Copy to clipboard");
		clip.addActionListener(actionListener);
		clip.addKeyListener(keyListener);
		clip.setMargin(new Insets(0, 5, 0, 5));
		p.add(clip);
		JButton exit = new JButton("Exit");
		exit.addActionListener(actionListener);
		exit.setPreferredSize(clip.getPreferredSize());
		p.add(exit);
		c.add(p, BorderLayout.SOUTH);
		dlg.pack();
		dlg.setLocationRelativeTo(parent);
		dlg.setVisible(true);
	}

	public void print() {
		PrinterJob pj = PrinterJob.getPrinterJob();
		PageFormat pf = pj.defaultPage();
		PageFormat newpf = pj.pageDialog(pf);
		if (pf != newpf) {
			pj.setPrintable(canvas, newpf);
			try {
				pj.print();
			} catch (PrinterException pe) {
			}
		}
	}
}
