package apt.erp.customerservice.ui.customerdatawindow.customerdataform;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import apt.erp.common.domain.EmailAddress;
import apt.erp.common.domain.Name;
import apt.erp.common.domain.PhoneNumber;
import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.customerservice.domain.Contact;

@SuppressWarnings("serial")
class ContactForm extends VerticalLayout {

	private final TextField nameField = FormFieldFactory.createFormTextField("Name", 200, true);
	private final TextField phoneField = FormFieldFactory.createFormTextField("Phone", 200, false);
	private final TextField emailField = FormFieldFactory.createFormTextField("Email", 200, false);

	private final List<Field<?>> dataFields = Arrays.asList(nameField, phoneField, emailField);
	
	ContactForm(Contact contact) {
		
		bindData(contact);
		
		createLayout();
	}
	
	Contact getContact() {
		return new Contact(new Name(nameField.getValue()), new PhoneNumber(phoneField.getValue()), new EmailAddress(emailField.getValue()));
	}
	
	private void bindData(Contact contact) {
		nameField.setPropertyDataSource(new ObjectProperty<String>(contact.name.value));
		phoneField.setPropertyDataSource(new ObjectProperty<String>(contact.phoneNumber.value));
		emailField.setPropertyDataSource(new ObjectProperty<String>(contact.emailAddress.value));
	}
	
	boolean isDataModified() {
		return dataFields.stream().anyMatch(Field::isModified);
	}
	
	boolean isValid() {
		return dataFields.stream().allMatch(Field::isValid);
	}
	
	private void createLayout() {
		setSpacing(true);
		setMargin(true);
		addComponents(nameField, phoneField, emailField);
		nameField.setSizeFull();
		setSizeFull();
	}
	
}
