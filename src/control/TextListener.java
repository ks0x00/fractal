package control;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public interface TextListener extends DocumentListener {
	void update();

	@Override
	default void removeUpdate(DocumentEvent e) {
		update();
	}

	@Override
	default void insertUpdate(DocumentEvent e) {
		update();
	}

	@Override
	default void changedUpdate(DocumentEvent e) {
		update();
	}
}
