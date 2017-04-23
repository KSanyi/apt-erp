package apt.erp.customerservice.ui;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.customerservice.domain.CustomerService;
import apt.erp.customerservice.ui.customerdatawindow.CreateCustomerDataWindow;

@SuppressWarnings("serial")
public class CustomersWindow extends Window {

	public CustomersWindow(CustomerService customerService, ZipTownMap zipTownMap) {
		super("Ügyfelek");
		center();
		setWidth("500px");
		
		CustomersTable customersTable = new CustomersTable(customerService, zipTownMap);

		TextField filter = new TextField("Szűrő");
		filter.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		//filter.addValueChangeListener(e -> customersTable.filter(e.getValue()));
		
		Button addButton = FormFieldFactory.createFormButton("Add customer", VaadinIcons.PLUS, ValoTheme.BUTTON_PRIMARY);
		addButton.addClickListener(e -> {
			CreateCustomerDataWindow createCustomerDataWindow = new CreateCustomerDataWindow(customerService, zipTownMap);
			createCustomerDataWindow.addCustomerChangeListener(c -> customersTable.refresh());
			UI.getCurrent().addWindow(createCustomerDataWindow);
		});
		
		VerticalLayout layout = LayoutFactory.createCenteredVerticalLayout(filter, customersTable, addButton);
		layout.setComponentAlignment(filter, Alignment.TOP_CENTER);
		layout.setComponentAlignment(addButton, Alignment.BOTTOM_CENTER);
		//layout.setSizeFull();
		setContent(layout);
	}
	
}
