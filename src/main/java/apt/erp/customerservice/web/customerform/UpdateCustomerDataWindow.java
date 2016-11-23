package apt.erp.customerservice.web.customerform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.vaadin.data.util.ObjectProperty;
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
import apt.erp.customerservice.domain.Address;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerService;
import apt.erp.customerservice.domain.Name;

@SuppressWarnings("serial")
public class UpdateCustomerDataWindow extends Window {

	protected final CustomerService customerService;
	protected final CustomerData customerData;
	
	private final TextField nameField = FormFieldFactory.createFormTextField("Name", 205, true);
	private final AddressForm addressForm;
	private final CheckBox invoiceAddressIsTheSameCheck = new CheckBox("Invoice address is the same");
	private final AddressForm invoiceAddressForm;
	protected final Button updateButton = new Button("Update", FontAwesome.SAVE);
	protected final Button deleteButton = new Button("Delete", FontAwesome.REMOVE);
	
	protected final List<CustomerDataChangeListener> customerDataChangeListeners = new ArrayList<>();
	
	public UpdateCustomerDataWindow(CustomerService customerService, CustomerData customerData, ZipTownMap zipTownMap) {
		this.customerService = customerService;
		this.customerData = customerData;
		setCaption("Customer Id: " + customerData.customerId.value);
		
		addressForm = new AddressForm("Address", customerData.address, zipTownMap);
		invoiceAddressForm = new AddressForm("Invoice address", customerData.invoiceAddress.orElse(null), zipTownMap);
		invoiceAddressIsTheSameCheck.addValueChangeListener(e -> invoiceAddressForm.setVisible(!(Boolean)e.getProperty().getValue()));
		
		bindData(customerData);
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
				customerService.updateCustomerData(createCustomerData());
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
		customerService.deleteCustomer(customerData.customerId);
		Notification.show("Customer has been deleted");
		notifyCustomerChangeListeners();
		this.close();
	}
	
	protected CustomerData createCustomerData() {
	    Optional<Address> invoiceAddress = invoiceAddressIsTheSameCheck.getValue() ?
	            Optional.empty() : Optional.of(invoiceAddressForm.getChangedAddress());
		return customerData.updated(new Name(nameField.getValue()),
				addressForm.getChangedAddress(), invoiceAddress, "", 
				Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
	}
	
	private void bindData(CustomerData customerData) {
		nameField.setPropertyDataSource(new ObjectProperty<String>(customerData.name.value));
		invoiceAddressIsTheSameCheck.setPropertyDataSource(new ObjectProperty<Boolean>(customerData.invoiceAddressIsTheSame()));
		invoiceAddressIsTheSameCheck.setBuffered(true); // to be able to check whether it was modified
	}
	
	private void createValidators() {
		nameField.addValidator(new HungarianNameValidator("Invalid name"));
	}
	
	private void createLayout() {
		HorizontalLayout buttonsLayout = new HorizontalLayout(updateButton, deleteButton);
		buttonsLayout.setSpacing(true);
		
		VerticalLayout layout = new VerticalLayout(nameField, addressForm, invoiceAddressIsTheSameCheck, invoiceAddressForm, buttonsLayout);
		layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);
		layout.setMargin(true);
		layout.setSpacing(true);
		setContent(layout);
	}
	
	protected boolean isDataModified() {
		return nameField.isModified() || addressForm.isDataModified() || invoiceAddressIsTheSameCheck.isModified() || invoiceAddressForm.isDataModified();
	}
	
	protected boolean isDataValid() {
		return nameField.isValid() && addressForm.isValid() && (invoiceAddressIsTheSameCheck.getValue() || invoiceAddressForm.isValid());
	}
	
	public void addCustomerChangeListener(CustomerDataChangeListener customerDataChangeListener){
	    customerDataChangeListeners.add(customerDataChangeListener);
	}
	
	protected void notifyCustomerChangeListeners(){
		customerDataChangeListeners.stream().forEach(listener -> listener.notifyCustomerDataChanged(customerData));
	}

}
