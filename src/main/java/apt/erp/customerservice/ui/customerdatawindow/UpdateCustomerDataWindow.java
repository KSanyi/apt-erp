package apt.erp.customerservice.ui.customerdatawindow;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.domain.ValidationError;
import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerService;
import apt.erp.customerservice.ui.customerdatawindow.customerdataform.CustomerDataForm;
import apt.erp.customerservice.ui.customerdatawindow.customerdataform.ZipTownMap;

@SuppressWarnings("serial")
public class UpdateCustomerDataWindow extends Window {

	private final CustomerService customerService;
	private final CustomerData customerData;
	
	private final CustomerDataForm customerDataForm;
	
	private final Button updateButton = FormFieldFactory.createFormButton("Mentés", FontAwesome.SAVE, ValoTheme.BUTTON_PRIMARY, click -> updateCustomerData());
	private final Button deleteButton = FormFieldFactory.createFormButton("Törlés", FontAwesome.REMOVE, ValoTheme.BUTTON_DANGER, click -> deleteCustomerData());
	
	private final List<CustomerDataChangeListener> customerDataChangeListeners = new ArrayList<>();
	
	public UpdateCustomerDataWindow(CustomerService customerService, CustomerData customerData, ZipTownMap zipTownMap) {
		this.customerService = customerService;
		this.customerData = customerData;
		setCaption("Ügyfél azonosító: " + customerData.customerId.value);
		
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
				} catch (ValidationError ex) {
					Notification.show("Validációs hiba: " + ex.getMessage(), Notification.Type.WARNING_MESSAGE);
				}
			} else {
				Notification.show("Hibás adatok", Notification.Type.WARNING_MESSAGE);
			}
		} else {
			this.close();
		}
	}
	
	private void deleteCustomerData() {
		customerService.deleteCustomer(customerData.customerId);
		Notification.show("Az ügyfél törölve");
		notifyCustomerChangeListeners();
		this.close();
	}
	
	private void createLayout() {
		HorizontalLayout buttonsLayout = LayoutFactory.createHorizontalLayout(updateButton, deleteButton);
		VerticalLayout layout = LayoutFactory.createCenteredVerticalLayout(customerDataForm, buttonsLayout);
		setContent(layout);
		setWidth("700px");
	}
	
	public void addCustomerChangeListener(CustomerDataChangeListener customerDataChangeListener) {
	    customerDataChangeListeners.add(customerDataChangeListener);
	}
	
	private void notifyCustomerChangeListeners() {
		customerDataChangeListeners.stream().forEach(listener -> listener.notifyCustomerDataChanged(customerData));
	}

}
