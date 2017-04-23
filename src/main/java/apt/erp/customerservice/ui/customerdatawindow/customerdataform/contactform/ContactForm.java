package apt.erp.customerservice.ui.customerdatawindow.customerdataform.contactform;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import apt.erp.common.domain.EmailAddress;
import apt.erp.common.domain.Name;
import apt.erp.common.domain.PhoneNumber;
import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.customerservice.domain.Contact;

@SuppressWarnings("serial")
class ContactForm extends CustomField<Contact> {

	private final TextField nameField = FormFieldFactory.createFormTextField("Név", 200, true);
	private final TextField phoneField = FormFieldFactory.createFormTextField("Telefon", 200, false);
	private final TextField emailField = FormFieldFactory.createFormTextField("Email", 200, false);

	ContactForm(Contact contact) {
		bindData(contact);
	}
	
	private void bindData(Contact contact) {
		nameField.setValue(contact.name.value);
		phoneField.setValue(contact.phoneNumber.value);
		emailField.setValue(contact.emailAddress.value);
	}
	
	@Override
	public Contact getValue() {
		return new Contact(new Name(nameField.getValue()), new PhoneNumber(phoneField.getValue()), new EmailAddress(emailField.getValue()));
	}

	@Override
	protected Component initContent() {
		VerticalLayout layout = new VerticalLayout(nameField, phoneField, emailField);
		nameField.setSizeFull();
		layout.setSizeFull();
		return layout;
	}

	@Override
	protected void doSetValue(Contact value) {
		throw new UnsupportedOperationException();
	}
	
}
