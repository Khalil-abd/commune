package ka.commune.view.control;

import com.jfoenix.controls.JFXTextField;

public class NumberField extends JFXTextField{

	@Override
	public void replaceText(int start, int end, String text) {
        String oldValue = getText();
        if (text.matches("\\d*(\\.\\d*)?")) {
            super.replaceText(start, end, text);
        }
        if (getText().length() > 18 ) {
            setText(oldValue);
        }
    }
	
	@Override
    public void replaceSelection(String text) {
        String oldValue = getText();
        if (text.matches("\\d*(\\.\\d*)?")) {
            super.replaceSelection(text);
        }
        if (getText().length() > 18 ) {
            setText(oldValue);
        }
    }
}
