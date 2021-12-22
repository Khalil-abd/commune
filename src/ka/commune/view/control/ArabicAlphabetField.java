package ka.commune.view.control;

import com.jfoenix.controls.JFXTextField;

public class ArabicAlphabetField extends JFXTextField{
	
	//^[\u0600-\u06FF\u0750-\u077F ]*$
	@Override
	public void replaceText(int start, int end, String text) {
        //String oldValue = getText();
        if (text.matches("^[\u0600-\u06FF\u0750-\u077F ]*$")) {
            super.replaceText(start, end, text);
        }
    }
	
	@Override
    public void replaceSelection(String text) {
        //String oldValue = getText();
        if (text.matches("^[\\u0600-\\u06FF\\u0750-\\u077F ]*$")) {
            super.replaceSelection(text);
        }
    }
}
