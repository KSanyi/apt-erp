package apt.erp.common.vaadin;

import java.util.Collection;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public interface FormFieldFactory {

	static TextField createFormTextField(String caption, int width, boolean required) {
		TextField textField = new TextField(caption);
		if(width == 0){
			textField.setSizeFull();
		} else {
			textField.setWidth(width + "px");
		}
		
		textField.setBuffered(true);
		textField.setRequired(required);
		if(required) {
			textField.setRequiredError(caption + " is required");
		}
		return textField;
	}
	
	static TextField createFormTextField(String caption, int width) {
		return createFormTextField(caption, width, false);
	}
	
	static TextArea createFormTextArea(String caption, int width){
		TextArea textArea = new TextArea(caption);
		if(width == 0){
			textArea.setSizeFull();
		} else {
			textArea.setWidth(width + "px");
		}
		return textArea;
	}
	
	static Button createFormButton(String caption, Resource icon, String style, ClickListener listener){
		Button button = new Button(caption, icon);
		button.addClickListener(listener);
		button.addStyleName(style);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		return button;
	}
	
	static ComboBox createComboBox(String caption, Collection<?> items) {
	    ComboBox combo = new ComboBox("Domain", items);
	    combo.setNullSelectionAllowed(false);
	    combo.setWidth("140px");
	    combo.setBuffered(true);
	    return combo;
	}
	
}
