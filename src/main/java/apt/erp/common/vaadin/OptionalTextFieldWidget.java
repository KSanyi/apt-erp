package apt.erp.common.vaadin;

import java.util.Optional;

import com.vaadin.data.Validator;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class OptionalTextFieldWidget extends CustomField<String>{

	private final CheckBox checkBox = new CheckBox();
	private final TextField textField = new TextField();
	
	public OptionalTextFieldWidget(String caption, String textFieldCaption, Optional<String> value) {
		checkBox.setCaption(caption);
		checkBox.setValue(value.isPresent());
		
		textField.setCaption(textFieldCaption);
		textField.setPropertyDataSource(new ObjectProperty<String>(value.orElse("")));
		if(!value.isPresent()) {
			textField.setVisible(false);
		}
		textField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
		
		checkBox.addValueChangeListener(e -> textField.setVisible((boolean)e.getProperty().getValue()));
		
		addValidator(new Validator() {
			@Override
			public void validate(Object value) throws InvalidValueException {
				if(checkBox.getValue() && textField.isEmpty()) {
					throw new InvalidValueException("Igazolványszám nem lehet üres");
				}
			}
		});
	}
	
	public Optional<String> getOptionalValue() {
		if(checkBox.getValue()) {
			return Optional.of(textField.getValue());
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	protected Component initContent() {
		return LayoutFactory.createVerticalLayout(checkBox, textField);
	}

	@Override
	public Class<? extends String> getType() {
		return String.class;
	}

}
