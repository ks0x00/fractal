package control;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class DataManager implements ActionListener {
	private Control control;
	private JList<String> list;
	private JDialog dialog;
	private DataModel model;

	public DataManager(Control control) {
		this.control = control;
		model = new DataModel();
		dialog = null;
		list = null;
	}

	public void showDialog() {
		if (dialog != null)
			return;
		dialog = new JDialog(control.parent, "Saved Fractals");
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeDialog();
			}
		});
		dialog.setContentPane(contentPane());
		dialog.setLocationRelativeTo(control.parent);
		dialog.setSize(450, 300);
		dialog.setVisible(true);
	}

	private void closeDialog() {
		list = null;
		dialog.dispose();
		dialog = null;
	}

	private JPanel contentPane() {
		JPanel pane = new JPanel(new BorderLayout());
		JPopupMenu popup = new JPopupMenu();
		popup.add("load").addActionListener(this);
		popup.add("delete").addActionListener(this);
		list = new JList<>(model);
		list.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger())
					popup.show(e.getComponent(), e.getX(), e.getY());
			}

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() >= 2) {
					draw();
				}
			}
		});
		KeyListener keyListener = new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER)
					draw();
				else if (ke.getKeyCode() == KeyEvent.VK_DELETE)
					remove();
				else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
					closeDialog();
			}
		};
		list.addKeyListener(keyListener);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pane.add(new JScrollPane(list));

		JPanel p = new JPanel();
		addButton(p, "\u2191", keyListener);
		addButton(p, "\u2193", keyListener);
		addButton(p, "load", keyListener);
		addButton(p, "delete", keyListener);
		addButton(p, "exit", keyListener);
		pane.add(p, BorderLayout.SOUTH);
		return pane;
	}

	private void addButton(JPanel p, String text, KeyListener listener) {
		JButton b = new JButton(text);
		b.setMargin(new Insets(0, 10, 0, 10));
		b.addActionListener(this);
		b.addKeyListener(listener);
		p.add(b);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("load"))
			draw();
		else if (e.getActionCommand().equals("delete"))
			remove();
		else if (e.getActionCommand().equals("exit"))
			closeDialog();
		else if (e.getActionCommand().equals("\u2191"))
			moveUp();
		else if (e.getActionCommand().equals("\u2193"))
			moveDown();
	}

	private void moveUp() {
		int index = list.getSelectedIndex();
		if (model.moveUp(index)) {
			list.setSelectedIndex(index - 1);
			list.updateUI();
		}
	}

	private void moveDown() {
		int index = list.getSelectedIndex();
		if (model.moveDown(index)) {
			list.setSelectedIndex(index + 1);
			list.updateUI();
		}
	}

	public void addDataToDataModel(String s) {
		model.add(s);
		if (list != null)
			list.updateUI();
	}

	private void remove() {
		if (model.remove(list.getSelectedIndex()))
			list.updateUI();
	}

	private void draw() {
		if (list.getSelectedIndex() >= 0) {
			control.drawData(list.getSelectedValue());
//			dialog.dispose();
		}
	}

	public void writeToFile() {
		model.writeToFile();
	}
}
