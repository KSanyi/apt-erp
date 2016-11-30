package apt.erp.translatorservice.ui.translatordatawindow.translatordataform;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import apt.erp.common.domain.EmailAddress;
import apt.erp.common.domain.Name;
import apt.erp.common.domain.PhoneNumber;
import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.translatorservice.domain.Translator;

@SuppressWarnings("serial")
public class TranslatorDataForm extends VerticalLayout {

	private final Translator translator;
	
	private final TextField nameField = FormFieldFactory.createFormTextField("Name", 300, true);
	private final TextField phoneField = FormFieldFactory.createFormTextField("Phone", 200, false);
    private final TextField emailField = FormFieldFactory.createFormTextField("Email", 200, false);
	private final TextArea commentField = new TextArea("Comments");
	
	private final List<Field<?>> dataFields = Arrays.asList(nameField, phoneField, emailField, commentField);
	
	public TranslatorDataForm(Translator translator) {
		this.translator = translator;
		createLayout();
		
		bindData(translator);
		createValidators();
	}
	
	private void bindData(Translator translator) {
		nameField.setPropertyDataSource(new ObjectProperty<String>(translator.name.value));
		phoneField.setPropertyDataSource(new ObjectProperty<String>(translator.phoneNumber.value));
		emailField.setPropertyDataSource(new ObjectProperty<String>(translator.emailAddress.value));
		commentField.setPropertyDataSource(new ObjectProperty<String>(translator.comment));
		commentField.setBuffered(true);
	}
	
	private void createValidators() {
	}
	
	private void createLayout() {
	    setSpacing(true);
		
	    addComponents(nameField, phoneField, emailField, commentField);
	    
		nameField.setSizeFull();
		commentField.setSizeFull();
		setSizeFull();
		nameField.focus();
	}
	
	public boolean isDataModified() {
		return dataFields.stream().anyMatch(Field::isModified);
	}
	
	public boolean isDataValid() {
		return dataFields.stream().allMatch(Field::isValid);
	}

	public Translator getTranslator() {
	    return translator.updated(new Name(nameField.getValue()), new PhoneNumber(phoneField.getValue()), new EmailAddress(emailField.getValue()),
	            Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), commentField.getValue());
	}
}
