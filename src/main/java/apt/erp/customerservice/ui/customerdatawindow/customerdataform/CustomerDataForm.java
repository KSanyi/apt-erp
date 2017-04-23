package apt.erp.customerservice.ui.customerdatawindow.customerdataform;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
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
public class CustomerDataForm extends CustomField<CustomerData> {

	private final CustomerData customerData;
	
	private final TextField nameField = FormFieldFactory.createFormTextField("Név", 300, true);
	private final TextField taxIdField = FormFieldFactory.createFormTextField("Adószám", 200, false);
	private final ComboBox<Domain> domainCombo = FormFieldFactory.createEnumComboBox("Terület", Domain.class);
	private final ComboBox<Language> languageCombo = FormFieldFactory.createEnumComboBox("Nyelv", Language.class);
	private final AddressTabSheet adressTabSheet;
	private final ContactsTabSheet contactsTabSheet;
	private final TextArea commentField = new TextArea("Megjegyzés");
	
	public CustomerDataForm(CustomerData customerData, ZipTownMap zipTownMap) {
		this.customerData = customerData;
		adressTabSheet = new AddressTabSheet(customerData.address, customerData.invoiceAddress, zipTownMap);
		contactsTabSheet = new ContactsTabSheet(customerData.contacts());
		
		bindData(customerData);
		createValidators();
	}
	
	private void bindData(CustomerData customerData) {
		nameField.setValue(customerData.name.value);
		taxIdField.setValue(customerData.taxId.value);
		domainCombo.setValue(customerData.mainDomain);
		languageCombo.setValue(customerData.mainLanguage);
		commentField.setValue(customerData.comment);
	}
	
	private void createValidators() {
		//taxIdField.addValidator(new RegexpValidator("\\d{8}-\\d-\\d{2}", "Hibás adószám"));
	}
	
	@Override
	public CustomerData getValue() {
		return customerData.updated(new TaxId(taxIdField.getValue()), new Name(nameField.getValue()),
				adressTabSheet.getAddress(), adressTabSheet.getInvoiceAddress(), commentField.getValue(), (Domain) domainCombo.getValue(),
				(Language) languageCombo.getValue(), contactsTabSheet.getValue());
	}

	@Override
	protected Component initContent() {
		GridLayout layout = new GridLayout(2, 2);
		layout.setSpacing(true);
		layout.addComponent(LayoutFactory.createVerticalLayoutWithNoMargin(nameField, taxIdField, new HorizontalLayout(domainCombo, languageCombo)));
		layout.addComponent(contactsTabSheet);
		layout.addComponent(adressTabSheet);
		layout.addComponent(commentField);
		nameField.setSizeFull();
		commentField.setSizeFull();
		commentField.setHeightUndefined();
		layout.setSizeFull();
		nameField.focus();
		return layout;
	}

	@Override
	protected void doSetValue(CustomerData value) {
		throw new UnsupportedOperationException();
	}
}
