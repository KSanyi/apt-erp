package apt.erp.translatorservice.ui.translatordatawindow.translatordataform;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import apt.erp.common.domain.EmailAddress;
import apt.erp.common.domain.Name;
import apt.erp.common.domain.PhoneNumber;
import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.projectservice.domain.LanguageServiceType;
import apt.erp.translatorservice.domain.PersonalData;
import apt.erp.translatorservice.domain.PersonalData.CommunicationChannel;

@SuppressWarnings("serial")
public class PersonalDataForm extends HorizontalLayout {

	private final TextField nameField = FormFieldFactory.createFormTextField("Név", 300, true);
	private final ComboBox mainServiceTypeCombo = FormFieldFactory.createComboBox("Foglalkozás", LanguageServiceType.all);
	private final TextField phoneField1 = FormFieldFactory.createFormTextField("Telefon 1", 200, false);
    private final TextField phoneField2 = FormFieldFactory.createFormTextField("Telefon 2", 200, false);
    private final TextField emailField1 = FormFieldFactory.createFormTextField("Email 1", 200, false);
    private final TextField emailField2 = FormFieldFactory.createFormTextField("Email 2", 200, false);
    private final TextField skypeIdField = FormFieldFactory.createFormTextField("Skype id", 200, false);
    private final ComboBox preferredCommunicationCombo = FormFieldFactory.createEnumComboBox("Preferált kommunikáció", CommunicationChannel.class);
    private final TextArea commentsField = new TextArea("Megjegyzések");
	
	private final List<Field<?>> dataFields = Arrays.asList(nameField, mainServiceTypeCombo, phoneField1, phoneField2, emailField1, emailField2, skypeIdField, preferredCommunicationCombo, commentsField);
	
	public PersonalDataForm(PersonalData personalData) {
		createLayout();
		
		bindData(personalData);
		createValidators();
	}
	
	private void bindData(PersonalData personalData) {
		nameField.setPropertyDataSource(new ObjectProperty<String>(personalData.name.value));
		mainServiceTypeCombo.setPropertyDataSource(new ObjectProperty<LanguageServiceType>(personalData.mainServiceType));
		phoneField1.setPropertyDataSource(new ObjectProperty<String>(personalData.phoneNumber1.value));
		phoneField2.setPropertyDataSource(new ObjectProperty<String>(personalData.phoneNumber2.value));
		emailField1.setPropertyDataSource(new ObjectProperty<String>(personalData.emailAddress1.value));
		emailField2.setPropertyDataSource(new ObjectProperty<String>(personalData.emailAddress2.value));
		skypeIdField.setPropertyDataSource(new ObjectProperty<String>(personalData.skypeId));
		preferredCommunicationCombo.setPropertyDataSource(new ObjectProperty<CommunicationChannel>(personalData.preferredCommunicationChannel));
		commentsField.setPropertyDataSource(new ObjectProperty<String>(personalData.comments));
	}
	
	private void createValidators() {
	}
	
	private void createLayout() {
	    setSpacing(true);
	    setMargin(true);
	    setSizeFull();
	    
	    VerticalLayout leftLayout = LayoutFactory.createVerticalLayoutWithNoMargin(nameField, 
	            LayoutFactory.createHorizontalLayout(phoneField1, phoneField2),
	            LayoutFactory.createHorizontalLayout(emailField1, emailField2),
	            skypeIdField,
	            preferredCommunicationCombo); 
	    
	    VerticalLayout rightLayout = LayoutFactory.createVerticalLayoutWithNoMargin(mainServiceTypeCombo, commentsField);
	    addComponents(leftLayout, rightLayout);
	    
		nameField.focus();
		mainServiceTypeCombo.setWidth("170px");
		preferredCommunicationCombo.setWidth("120px");
		commentsField.setWidth("400px");
		commentsField.setRows(10);
	}
	
	public boolean isDataModified() {
		return dataFields.stream().anyMatch(Field::isModified);
	}
	
	public boolean isDataValid() {
		return dataFields.stream().allMatch(Field::isValid);
	}

    public PersonalData getPersonalData() {
	    
	    return new PersonalData(new Name(nameField.getValue()), (LanguageServiceType)mainServiceTypeCombo.getValue(), new PhoneNumber(phoneField1.getValue()), new PhoneNumber(phoneField2.getValue()), 
	            new EmailAddress(emailField1.getValue()), new EmailAddress(emailField2.getValue()), skypeIdField.getValue(),
	            (CommunicationChannel)preferredCommunicationCombo.getValue(), commentsField.getValue());
	}
}
