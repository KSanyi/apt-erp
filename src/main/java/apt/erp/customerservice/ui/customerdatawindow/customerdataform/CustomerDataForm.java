package apt.erp.customerservice.ui.customerdatawindow.customerdataform;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.Domain;
import apt.erp.customerservice.domain.Name;
import apt.erp.customerservice.domain.TaxId;
import apt.erp.projectservice.domain.Language;

@SuppressWarnings("serial")
public class CustomerDataForm extends VerticalLayout {

	private final CustomerData customerData;
	
	private final TextField nameField = FormFieldFactory.createFormTextField("Name", 300, true);
	private final TextField taxIdField = FormFieldFactory.createFormTextField("Tax Id", 200, false);
	private final ComboBox domainCombo = FormFieldFactory.createComboBox("Domain", Domain.all);
	private final ComboBox languageCombo = FormFieldFactory.createComboBox("Language", Language.all);
	private final AddressTabSheet adressTabsheet;
	private final TextArea commentField = new TextArea("Comments");
	
	private final List<Field<?>> dataFields = Arrays.asList(nameField, taxIdField, domainCombo, languageCombo, commentField);
	
	public CustomerDataForm(CustomerData customerData, ZipTownMap zipTownMap) {
		this.customerData = customerData;
		adressTabsheet = new AddressTabSheet(customerData.address, customerData.invoiceAddress, zipTownMap);
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
	}
	
	private void createValidators() {
		taxIdField.addValidator(new RegexpValidator("\\d{8}-\\d-\\d{2}", "Invalid tax id"));
	}
	
	private void createLayout() {
		setSpacing(true);
		
		commentField.setSizeFull();
		
		LayoutFactory.createHorizontalLayout(domainCombo, languageCombo);
		
		addComponents(nameField, taxIdField, LayoutFactory.createHorizontalLayout(domainCombo, languageCombo), adressTabsheet, commentField);
	}
	
	public boolean isDataModified() {
		return dataFields.stream().anyMatch(Field::isModified) || adressTabsheet.isDataModified();
	}
	
	public boolean isDataValid() {
		return dataFields.stream().allMatch(Field::isValid) && adressTabsheet.isValid();
	}

	public CustomerData getCustomerData() {
		return customerData.updated(new TaxId(taxIdField.getValue()), new Name(nameField.getValue()),
				adressTabsheet.getAddress(), adressTabsheet.getInvoiceAddress(), commentField.getValue(), (Domain) domainCombo.getValue(),
				(Language) languageCombo.getValue(), Collections.emptyList());

	}
}
