package apt.erp.translatorservice.ui.translatordatawindow.translatordataform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

import apt.erp.common.domain.EmailAddress;
import apt.erp.common.domain.Name;
import apt.erp.common.domain.PhoneNumber;
import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.projectservice.domain.ServiceType;
import apt.erp.translatorservice.domain.ContactData;

@SuppressWarnings("serial")
public class ContactDataForm extends VerticalLayout {

	private final TextField nameField = FormFieldFactory.createFormTextField("Név", 300, true);
	private final TextField phoneField1 = FormFieldFactory.createFormTextField("Telefon 1", 200, false);
    private final TextField phoneField2 = FormFieldFactory.createFormTextField("Telefon 2", 200, false);
    private final TextField emailField1 = FormFieldFactory.createFormTextField("Email 1", 200, false);
    private final TextField emailField2 = FormFieldFactory.createFormTextField("Email 2", 200, false);
    private final TwinColSelect serviceTypeSelect = new TwinColSelect("Szolgáltatások", ServiceType.all);
	
	private final List<Field<?>> dataFields = Arrays.asList(nameField, phoneField1, phoneField2, emailField1, emailField2, serviceTypeSelect);
	
	public ContactDataForm(ContactData contactData) {
		createLayout();
		
		bindData(contactData);
		createValidators();
	}
	
	private void bindData(ContactData contactData) {
		nameField.setPropertyDataSource(new ObjectProperty<String>(contactData.name.value));
		phoneField1.setPropertyDataSource(new ObjectProperty<String>(contactData.phoneNumber1.value));
		phoneField2.setPropertyDataSource(new ObjectProperty<String>(contactData.phoneNumber2.value));
		emailField1.setPropertyDataSource(new ObjectProperty<String>(contactData.emailAddress1.value));
		emailField2.setPropertyDataSource(new ObjectProperty<String>(contactData.emailAddress2.value));
		serviceTypeSelect.setPropertyDataSource(new ObjectProperty<Set<ServiceType>>(new HashSet<>(contactData.serviceTypes)));
		serviceTypeSelect.setBuffered(true);
		serviceTypeSelect.setMultiSelect(true);
	}
	
	private void createValidators() {
	}
	
	private void createLayout() {
	    setSpacing(true);
	    setMargin(true);
	    setSizeFull();
	    
	    addComponents(nameField, 
	            LayoutFactory.createHorizontalLayout(phoneField1, phoneField2),
	            LayoutFactory.createHorizontalLayout(emailField1, emailField2),
	            serviceTypeSelect);
	    
		nameField.focus();
		
		serviceTypeSelect.setLeftColumnCaption("Összes");
		serviceTypeSelect.setRightColumnCaption("Ajánlott");
		serviceTypeSelect.setWidth("450px");
		serviceTypeSelect.setHeight("120px");
	}
	
	public boolean isDataModified() {
		return dataFields.stream().anyMatch(Field::isModified);
	}
	
	public boolean isDataValid() {
		return dataFields.stream().allMatch(Field::isValid);
	}

	@SuppressWarnings("unchecked")
    public ContactData getContactData() {
	    
	    Set<ServiceType> selectedServiceTypes = (Set<ServiceType>)serviceTypeSelect.getValue();
	    
	    return new ContactData(new Name(nameField.getValue()), new PhoneNumber(phoneField1.getValue()), new PhoneNumber(phoneField2.getValue()), 
	            new EmailAddress(emailField1.getValue()), new EmailAddress(emailField2.getValue()), new ArrayList<>(selectedServiceTypes));
	}
}
