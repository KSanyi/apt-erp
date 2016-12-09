package apt.erp.customerservice.ui.customerdatawindow.customerdataform;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import apt.erp.common.domain.Name;
import apt.erp.common.domain.TaxId;
import apt.erp.common.vaadin.AddressTabSheet;
import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.Domain;
import apt.erp.customerservice.ui.customerdatawindow.customerdataform.contactform.ContactsTabSheet;
import apt.erp.projectservice.domain.Language;

@SuppressWarnings("serial")
public class CustomerDataForm extends GridLayout {

	private final CustomerData customerData;
	
	private final TextField nameField = FormFieldFactory.createFormTextField("Név", 300, true);
	private final TextField taxIdField = FormFieldFactory.createFormTextField("Adószám", 200, false);
	private final ComboBox domainCombo = FormFieldFactory.createComboBox("Terület", Domain.all);
	private final ComboBox languageCombo = FormFieldFactory.createComboBox("Nyelv", Language.all);
	private final AddressTabSheet adressTabSheet;
	private final ContactsTabSheet contactsTabSheet;
	private final TextArea commentField = new TextArea("Megjegyzés");
	
	private final List<Field<?>> dataFields = Arrays.asList(nameField, taxIdField, domainCombo, languageCombo, commentField);
	
	public CustomerDataForm(CustomerData customerData, ZipTownMap zipTownMap) {
		this.customerData = customerData;
		adressTabSheet = new AddressTabSheet(customerData.address, customerData.invoiceAddress, zipTownMap);
		contactsTabSheet = new ContactsTabSheet(customerData.contacts());
		createLayout();
		
		bindData(customerData);
		createValidators();
	}
	
	private void bindData(CustomerData customerData) {
		nameField.setPropertyDataSource(new ObjectProperty<String>(customerData.name.value));
		taxIdField.setPropertyDataSource(new ObjectProperty<String>(customerData.taxId.value));
		domainCombo.setPropertyDataSource(new ObjectProperty<Domain>(customerData.mainDomain));
		languageCombo.setPropertyDataSource(new ObjectProperty<Language>(customerData.mainLanguage));
		commentField.setPropertyDataSource(new ObjectProperty<String>(customerData.comment));
		commentField.setBuffered(true);
	}
	
	private void createValidators() {
		taxIdField.addValidator(new RegexpValidator("\\d{8}-\\d-\\d{2}", "Hibás adószám"));
	}
	
	private void createLayout() {
	    setSpacing(true);
		
		setColumns(2);
		setRows(2);
		
		addComponent(LayoutFactory.createVerticalLayoutWithNoMargin(nameField, taxIdField, LayoutFactory.createHorizontalLayout(domainCombo, languageCombo)));
		addComponent(contactsTabSheet);
		addComponent(adressTabSheet);
		addComponent(commentField);
		nameField.setSizeFull();
		commentField.setSizeFull();
		setSizeFull();
		nameField.focus();
	}
	
	public boolean isDataModified() {
		return dataFields.stream().anyMatch(Field::isModified) || adressTabSheet.isDataModified() || contactsTabSheet.isDataModified() || commentField.isModified();
	}
	
	public boolean isDataValid() {
		return dataFields.stream().allMatch(Field::isValid) && adressTabSheet.isValid() && contactsTabSheet.isValid();
	}

	public CustomerData getCustomerData() {
		return customerData.updated(new TaxId(taxIdField.getValue()), new Name(nameField.getValue()),
				adressTabSheet.getAddress(), adressTabSheet.getInvoiceAddress(), commentField.getValue(), (Domain) domainCombo.getValue(),
				(Language) languageCombo.getValue(), contactsTabSheet.getContacts());

	}
}
