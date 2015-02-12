package utils;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class HintTextField extends JTextField implements FocusListener {

	private static final long serialVersionUID = 1L;
	private String hint;
	private boolean showingHint;

	public HintTextField(final String hint) {
		super(hint);
		this.hint = hint;
		this.showingHint = true;
		super.addFocusListener(this);
	}
	
	public void setHint(String newHint) {
		this.hint = newHint;
	}
	
	public String getHint() {
		return this.hint;
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (this.getText().isEmpty()) {
			super.setForeground(Color.BLACK);
			super.setText("");
			showingHint = false;
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (this.getText().isEmpty()) {
			super.setForeground(Color.GRAY);
			super.setText(hint);
			showingHint = true;
		}
	}

	@Override
	public String getText() {
		return showingHint ? "" : super.getText();
	}
}