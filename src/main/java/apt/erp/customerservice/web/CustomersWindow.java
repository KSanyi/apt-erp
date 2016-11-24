package apt.erp.customerservice.web;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.customerservice.domain.CustomerService;
import apt.erp.customerservice.web.customerform.CreateCustomerDataWindow;
import apt.erp.customerservice.web.customerform.ZipTownMap;

@SuppressWarnings("serial")
public class CustomersWindow extends Window {

	public CustomersWindow(CustomerService customerService, ZipTownMap zipTownMap) {
		super("Customers");
		center();
		
		CustomersTable customersTable = new CustomersTable(customerService, zipTownMap);

		TextField filter = new TextField("Filter");
		filter.addTextChangeListener(textChangeEvent -> customersTable.filter(textChangeEvent.getText()));
		
		Button addButton = FormFieldFactory.createFormButton("Add customer", FontAwesome.PLUS, ValoTheme.BUTTON_PRIMARY,
				e -> {
					CreateCustomerDataWindow createCustomerDataWindow = new CreateCustomerDataWindow(customerService, zipTownMap);
					createCustomerDataWindow.addCustomerChangeListener(c -> customersTable.refresh());
					UI.getCurrent().addWindow(createCustomerDataWindow);	
				});
		
		setContent(LayoutFactory.createCenteredVerticalLayout(filter, customersTable, addButton));
	}
	
}
