package apt.erp.customerservice.ui.customerdatawindow;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerService;
import apt.erp.customerservice.domain.CustomerServiceException;
import apt.erp.customerservice.ui.customerdatawindow.customerdataform.CustomerDataForm;
import apt.erp.customerservice.ui.customerdatawindow.customerdataform.ZipTownMap;

@SuppressWarnings("serial")
public class UpdateCustomerDataWindow extends Window {

	private final CustomerService customerService;
	private final CustomerData customerData;
	
	private final CustomerDataForm customerDataForm;
	
	private final Button updateButton = FormFieldFactory.createFormButton("Update", FontAwesome.SAVE, ValoTheme.BUTTON_PRIMARY, event -> updateCustomerData());
	private final Button deleteButton = FormFieldFactory.createFormButton("Delete", FontAwesome.REMOVE, ValoTheme.BUTTON_DANGER, event -> deleteCustomerData());
	
	private final List<CustomerDataChangeListener> customerDataChangeListeners = new ArrayList<>();
	
	public UpdateCustomerDataWindow(CustomerService customerService, CustomerData customerData, ZipTownMap zipTownMap) {
		this.customerService = customerService;
		this.customerData = customerData;
		setCaption("Customer Id: " + customerData.customerId.value);
		
		customerDataForm = new CustomerDataForm(customerData, zipTownMap);
		createLayout();
		
		center();
	}
	
	private void updateCustomerData() {
		if(customerDataForm.isDataModified()) {
			if(customerDataForm.isDataValid()) {
				try {
					customerService.updateCustomerData(customerDataForm.getCustomerData());
					Notification.show("Customer has been updated");
					notifyCustomerChangeListeners();
					this.close();
				} catch (CustomerServiceException ex) {
					Notification.show("Validation error: " + ex.getMessage(), Notification.Type.WARNING_MESSAGE);
				}
			} else {
				Notification.show("Fix data errors", Notification.Type.WARNING_MESSAGE);
			}
		} else {
			this.close();
		}
	}
	
	private void deleteCustomerData() {
		customerService.deleteCustomer(customerData.customerId);
		Notification.show("Customer has been deleted");
		notifyCustomerChangeListeners();
		this.close();
	}
	
	private void createLayout() {
		HorizontalLayout buttonsLayout = LayoutFactory.createHorizontalLayout(updateButton, deleteButton);
		setContent(LayoutFactory.createCenteredVerticalLayout(customerDataForm, buttonsLayout));
	}
	
	public void addCustomerChangeListener(CustomerDataChangeListener customerDataChangeListener) {
	    customerDataChangeListeners.add(customerDataChangeListener);
	}
	
	private void notifyCustomerChangeListeners() {
		customerDataChangeListeners.stream().forEach(listener -> listener.notifyCustomerDataChanged(customerData));
	}

}
