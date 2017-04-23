package apt.erp.customerservice.ui;

import java.util.Set;

import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;

import apt.erp.common.vaadin.KitsTheme;
import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerService;
import apt.erp.customerservice.ui.customerdatawindow.UpdateCustomerDataWindow;

@SuppressWarnings("serial")
class CustomersTable extends Grid<CustomerData> {

	private final CustomerService customerService;
	
	public CustomersTable(CustomerService customerService, ZipTownMap zipTownMap) {
	    this.customerService = customerService;
		
	    addColumn(c -> c.name).setCaption("Név").setId("name");
	    addColumn(c -> c.address.toString()).setCaption("Cím");

	    sort("name");
	    
		refresh();

		addStyleName(KitsTheme.GRID_SMALL);
		setSizeFull();

		addItemClickListener(event -> {
			CustomerData customerData = event.getItem();
			UpdateCustomerDataWindow updateCustomerDataWindow = new UpdateCustomerDataWindow(customerService, customerData, zipTownMap);
			updateCustomerDataWindow.addCustomerChangeListener(c -> refresh());
			UI.getCurrent().addWindow(updateCustomerDataWindow);
		});
	}
	
	public void refresh() {
		Set<CustomerData> selectedItems = getSelectedItems();
		
		setItems(customerService.loadAllCustomers());
		
		if(!selectedItems.isEmpty()) {
			select(selectedItems.iterator().next());
		}
	}
	
	/*
	public void filter(String filterString) {
		IndexedContainer container = (IndexedContainer)getContainerDataSource();
		container.removeAllContainerFilters();
		if(!filterString.isEmpty()) {
			container.addContainerFilter(new CustomerFilter(filterString));
		}
	}
	
	private static class CustomerFilter implements Filter {
		private final String filterString;
		public CustomerFilter(String filterString) {
			this.filterString = filterString;
		}
		
		public boolean passesFilter(Object itemId, Item item) {
		    CustomerData customerData = (CustomerData)itemId;
		    return customerData.matches(filterString);
		}

		public boolean appliesToProperty(Object propertyId) {
			return false;
		}
	}
	*/

}
