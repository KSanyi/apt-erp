package apt.erp.customerservice.ui.customerdatawindow;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.domain.ValidationError;
import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerService;
import apt.erp.customerservice.ui.customerdatawindow.customerdataform.CustomerDataForm;

@SuppressWarnings("serial")
public class UpdateCustomerDataWindow extends Window {

	private final CustomerService customerService;
	private final CustomerData customerData;
	
	private final CustomerDataForm customerDataForm;
	
	private final Button updateButton = FormFieldFactory.createFormButton("Mentés", FontAwesome.SAVE, ValoTheme.BUTTON_PRIMARY, click -> updateCustomerData());
	private final Button deleteButton = FormFieldFactory.createFormButton("Törlés", VaadinIcons.CLOSE_CIRCLE, ValoTheme.BUTTON_DANGER, click -> deleteCustomerData());
	
	private final List<CustomerDataChangeListener> customerDataChangeListeners = new ArrayList<>();
	
	public UpdateCustomerDataWindow(CustomerService customerService, CustomerData customerData, ZipTownMap zipTownMap) {
		this.customerService = customerService;
		this.customerData = customerData;
		setCaption("Ügyfél azonosító: " + customerData.customerId.value);
		
		customerDataForm = new CustomerDataForm(customerData, zipTownMap);
		setContent(createLayout());
		
		center();
		setWidth("700px");
		setHeight("600");
	}
	
	private void updateCustomerData() {
		try {
			customerService.updateCustomerData(customerDataForm.getValue());
			Notification.show("Customer has been updated");
			notifyCustomerChangeListeners();
			this.close();
		} catch (ValidationError ex) {
			Notification.show("Validációs hiba: " + ex.getMessage(), Notification.Type.WARNING_MESSAGE);
		}
	}
	
	private void deleteCustomerData() {
		customerService.deleteCustomer(customerData.customerId);
		Notification.show("Az ügyfél törölve");
		notifyCustomerChangeListeners();
		this.close();
	}
	
	private VerticalLayout createLayout() {
		HorizontalLayout buttonBar = new HorizontalLayout(updateButton, deleteButton);
		VerticalLayout layout = new VerticalLayout(customerDataForm, buttonBar);
		layout.setComponentAlignment(buttonBar, Alignment.BOTTOM_CENTER);
		layout.setSizeFull();
		return layout;
	}
	
	public void addCustomerChangeListener(CustomerDataChangeListener customerDataChangeListener) {
	    customerDataChangeListeners.add(customerDataChangeListener);
	}
	
	private void notifyCustomerChangeListeners() {
		customerDataChangeListeners.stream().forEach(listener -> listener.notifyCustomerDataChanged(customerData));
	}

}
