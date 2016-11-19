package apt.erp.customerservice.web.customerform;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.customerservice.domain.Customer;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerService;
import apt.erp.customerservice.domain.EmailAddress;
import apt.erp.customerservice.domain.Name;
import apt.erp.customerservice.domain.PhoneNumber;

@SuppressWarnings("serial")
public class UpdateCustomerDataWindow extends Window {

	protected final CustomerService customerService;
	protected final Customer customer;
	
	private final TextField nameField = FormFieldFactory.createFormTextField("Name", 205, true);
	private final TextField phoneField = FormFieldFactory.createFormTextField("Phone number", 100);
	private final TextField emailField = FormFieldFactory.createFormTextField("Email", 205);
	private final AddressForm addressForm;
	private final CheckBox invoiceAddressIsTheSameCheck = new CheckBox("Invoice address is the same");
	private final AddressForm invoiceAddressForm;
	protected final Button updateButton = new Button("Update", FontAwesome.SAVE);
	protected final Button deleteButton = new Button("Delete", FontAwesome.REMOVE);
	
	protected final List<CustomerChangeListener> customerChangeListeners = new ArrayList<>();
	
	public UpdateCustomerDataWindow(CustomerService customerService, Customer customer, ZipTownMap zipTownMap) {
		this.customerService = customerService;
		this.customer = customer;
		setCaption("Customer Id: " + customer.customerId.value);
		
		addressForm = new AddressForm("Address", customer.customerData.address, zipTownMap);
		invoiceAddressForm = new AddressForm("Invoice address", customer.customerData.invoiceAddress, zipTownMap);
		invoiceAddressIsTheSameCheck.addValueChangeListener(e -> invoiceAddressForm.setVisible(!(Boolean)e.getProperty().getValue()));
		
		bindData(customer);
		createValidators();
		
		createLayout();
		
		updateButton.addClickListener(event -> saveData());
		updateButton.setStyleName(Reindeer.BUTTON_SMALL);
		deleteButton.addClickListener(event -> deleteCustomer());
		deleteButton.setStyleName(Reindeer.BUTTON_SMALL);
		
		setModal(true);
	}
	
	protected void saveData() {
		if(isDataModified()){
			if(isDataValid()) {
				CustomerData updatedCcustomerData = createCustomerData();
				customerService.updateCustomerData(customer, updatedCcustomerData);
				Notification.show("Customer has been updated");
				notifyCustomerChangeListeners();
				this.close();
			} else {
				Notification.show("Fix data errors", Notification.Type.WARNING_MESSAGE);
			}
		} else {
			this.close();
		}
	}
	
	private void deleteCustomer() {
		customerService.deleteCustomer(customer.customerId);
		Notification.show("Customer has been deleted");
		notifyCustomerChangeListeners();
		this.close();
	}
	
	protected CustomerData createCustomerData() {
		return new CustomerData(new Name(nameField.getValue()),
				addressForm.getChangedAddress(), invoiceAddressIsTheSameCheck.getValue(), invoiceAddressForm.getChangedAddress(),
				new EmailAddress(emailField.getValue()), new PhoneNumber(phoneField.getValue()), "");
	}
	
	private void bindData(Customer customer) {
		nameField.setPropertyDataSource(new ObjectProperty<String>(customer.customerData.name.value));
		phoneField.setPropertyDataSource(new ObjectProperty<String>(customer.customerData.phoneNumber.value));
		emailField.setPropertyDataSource(new ObjectProperty<String>(customer.customerData.emailAddress.value));
		invoiceAddressIsTheSameCheck.setPropertyDataSource(new ObjectProperty<Boolean>(customer.customerData.invoiceAddressIsTheSame));
		invoiceAddressIsTheSameCheck.setBuffered(true);
	}
	
	private void createValidators() {
		emailField.addValidator(new EmailValidator("Invalid email address"));
		nameField.addValidator(new HungarianNameValidator("Invalid name"));
	}
	
	private void createLayout() {
		HorizontalLayout buttonsLayout = new HorizontalLayout(updateButton, deleteButton);
		buttonsLayout.setSpacing(true);
		
		VerticalLayout layout = new VerticalLayout(nameField, phoneField, emailField, addressForm, invoiceAddressIsTheSameCheck, invoiceAddressForm, buttonsLayout);
		layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);
		layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);
	}
	
	protected boolean isDataModified() {
		return nameField.isModified() || phoneField.isModified() || emailField.isModified() || addressForm.isDataModified() || invoiceAddressIsTheSameCheck.isModified() || invoiceAddressForm.isDataModified();
	}
	
	protected boolean isDataValid() {
		return nameField.isValid() && phoneField.isValid() && emailField.isValid() && addressForm.isValid() && (invoiceAddressIsTheSameCheck.getValue() || invoiceAddressForm.isValid());
	}
	
	public void addCustomerChangeListener(CustomerChangeListener customerChangeListener){
		customerChangeListeners.add(customerChangeListener);
	}
	
	protected void notifyCustomerChangeListeners(){
		customerChangeListeners.stream().forEach(listener -> listener.notifyCustomerChanged(customer));
	}

}
