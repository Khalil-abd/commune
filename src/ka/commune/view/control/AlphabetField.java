package ka.commune.view.control;

import com.jfoenix.controls.JFXTextField;

public class AlphabetField extends JFXTextField{

	//^[a-zA-Z ]*$
	@Override
	public void replaceText(int start, int end, String text) {
        //String oldValue = getText();
        if (text.matches("^[a-zA-Z ]*$")) {
            super.replaceText(start, end, text);
        }
    }
	
	@Override
    public void replaceSelection(String text) {
        //String oldValue = getText();
        if (text.matches("^[a-zA-Z ]*$")) {
            super.replaceSelection(text);
        }
    }
}
