package apt.erp.translatorservice.ui.translatordatawindow.translatordataform;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
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
public class PersonalDataForm extends CustomField<PersonalData> {

	private final TextField nameField = FormFieldFactory.createFormTextField("Név", 300, true);
	private final ComboBox<LanguageServiceType> mainServiceTypeCombo = FormFieldFactory.createComboBox("Foglalkozás", LanguageServiceType.all);
	private final TextField phoneField1 = FormFieldFactory.createFormTextField("Telefon 1", 150, false);
    private final TextField phoneField2 = FormFieldFactory.createFormTextField("Telefon 2", 150, false);
    private final TextField emailField1 = FormFieldFactory.createFormTextField("Email 1", 200, false);
    private final TextField emailField2 = FormFieldFactory.createFormTextField("Email 2", 200, false);
    private final TextField skypeIdField = FormFieldFactory.createFormTextField("Skype id", 200, false);
    private final ComboBox<CommunicationChannel> preferredCommunicationCombo = FormFieldFactory.createEnumComboBox("Preferált kommunikáció", CommunicationChannel.class);
    private final TextArea commentsField = new TextArea("Megjegyzések");
	
	public PersonalDataForm(PersonalData personalData) {
		
		bindData(personalData);
	}
	
	private void bindData(PersonalData personalData) {
		nameField.setValue(personalData.name.value);
		mainServiceTypeCombo.setValue(personalData.mainServiceType);
		phoneField1.setValue(personalData.phoneNumber1.value);
		phoneField2.setValue(personalData.phoneNumber2.value);
		emailField1.setValue(personalData.emailAddress1.value);
		emailField2.setValue(personalData.emailAddress2.value);
		skypeIdField.setValue(personalData.skypeId);
		preferredCommunicationCombo.setValue(personalData.preferredCommunicationChannel);
		commentsField.setValue(personalData.comments);
	}
	
	@Override
	public PersonalData getValue() {
		 return new PersonalData(new Name(nameField.getValue()), mainServiceTypeCombo.getValue(), new PhoneNumber(phoneField1.getValue()), new PhoneNumber(phoneField2.getValue()), 
		            new EmailAddress(emailField1.getValue()), new EmailAddress(emailField2.getValue()), skypeIdField.getValue(),
		            preferredCommunicationCombo.getValue(), commentsField.getValue());
	}

	@Override
	protected Component initContent() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeFull();
	    
	    VerticalLayout leftLayout = LayoutFactory.createVerticalLayoutWithNoMargin(nameField, 
	            LayoutFactory.createHorizontalLayout(phoneField1, phoneField2),
	            LayoutFactory.createHorizontalLayout(emailField1, emailField2),
	            skypeIdField,
	            preferredCommunicationCombo); 
	    
	    VerticalLayout rightLayout = LayoutFactory.createVerticalLayoutWithNoMargin(mainServiceTypeCombo, commentsField);
	    layout.addComponents(leftLayout, rightLayout);
	    layout.setMargin(true);
	    
		nameField.focus();
		mainServiceTypeCombo.setWidth("170px");
		preferredCommunicationCombo.setWidth("120px");
		commentsField.setWidth("400px");
		commentsField.setRows(10);
		
		return layout;
	}

	@Override
	protected void doSetValue(PersonalData value) {
		throw new UnsupportedOperationException();
	}
}
