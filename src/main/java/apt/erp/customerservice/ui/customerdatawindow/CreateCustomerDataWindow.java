package apt.erp.customerservice.ui.customerdatawindow;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerService;
import apt.erp.customerservice.ui.customerdatawindow.customerdataform.CustomerDataForm;
import apt.erp.customerservice.ui.customerdatawindow.customerdataform.ZipTownMap;

@SuppressWarnings("serial")
public class CreateCustomerDataWindow extends Window {

	private final CustomerService customerService;
	
	private final CustomerDataForm customerDataForm;
	
	private final Button saveButton = FormFieldFactory.createFormButton("Create", FontAwesome.SAVE, ValoTheme.BUTTON_PRIMARY, click -> createCustomerData());
	
	private final List<CustomerDataChangeListener> customerDataChangeListeners = new ArrayList<>();
	
	public CreateCustomerDataWindow(CustomerService customerService, ZipTownMap zipTownMap) {
		this.customerService = customerService;
		setCaption("New Customer");
		
		customerDataForm = new CustomerDataForm(CustomerData.createEmpty(), zipTownMap);
		setContent(LayoutFactory.createCenteredVerticalLayout(customerDataForm, saveButton));
		setWidth("700px");
		setHeight("600px");
		center();
	}
	
	private void createCustomerData() {
		if(customerDataForm.isDataValid()) {
			CustomerData customerData = customerDataForm.getCustomerData();
			customerService.createCustomer(customerData);
			Notification.show("Customer has been created");
			notifyCustomerChangeListeners(customerData);
			this.close();
		} else {
			Notification.show("Fix data errors", Notification.Type.WARNING_MESSAGE);
		}
	}
	
	public void addCustomerChangeListener(CustomerDataChangeListener customerDataChangeListener) {
	    customerDataChangeListeners.add(customerDataChangeListener);
	}
	
	private void notifyCustomerChangeListeners(CustomerData customerData) {
		customerDataChangeListeners.stream().forEach(listener -> listener.notifyCustomerDataChanged(customerData));
	}
	
}
	
