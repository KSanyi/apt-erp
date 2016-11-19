package apt.erp.customerservice.web.customerform;

import com.vaadin.ui.Notification;

import apt.erp.customerservice.domain.Customer;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerService;

@SuppressWarnings("serial")
public class CreateCustomerDataWindow extends UpdateCustomerDataWindow {

	public CreateCustomerDataWindow(CustomerService customerService, ZipTownMap zipTownMap) {
		super(customerService, Customer.createEmptyCustomer(), zipTownMap);
		deleteButton.setVisible(false);
		updateButton.setCaption("Save");
	}
	
	@Override
	protected void saveData() {
		if(isDataValid()) {
			CustomerData customerData = createCustomerData();
			customerService.createCustomer(customerData);
			Notification.show("Customer has been created");
			notifyCustomerChangeListeners();
			this.close();
		} else {
			Notification.show("Fix data errors", Notification.Type.WARNING_MESSAGE);
		}
	}
	
}
	
