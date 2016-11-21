package apt.erp.customerservice.web;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

import apt.erp.customerservice.domain.Customer;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerService;
import apt.erp.customerservice.web.customerform.UpdateCustomerDataWindow;
import apt.erp.customerservice.web.customerform.ZipTownMap;

@SuppressWarnings("serial")
class CustomersTable extends Table {

	private final CustomerService customerService;
	
	public CustomersTable(CustomerService customerService, ZipTownMap zipTownMap) {
		this.customerService = customerService;
		
		addContainerProperty("Name", String.class, null);
		addContainerProperty("Address", String.class, null);

		refresh();
		
		setPageLength(20);
		setSelectable(true);

		addItemClickListener(event -> {
			Customer customer = (Customer)event.getItemId();
			UpdateCustomerDataWindow updateCustomerDataWindow = new UpdateCustomerDataWindow(customerService, customer, zipTownMap);
			updateCustomerDataWindow.addCustomerChangeListener(c -> refresh());
			UI.getCurrent().addWindow(updateCustomerDataWindow);
		});
	}
	
	public void refresh() {
		removeAllItems();
		for(Customer customer : customerService.loadAllCustomers()) {
			CustomerData data = customer.customerData;
			addItem(new Object[]{data.name.toString(), data.address.toString()}, customer);
		}
	}
	
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
			Customer customer = (Customer)itemId;
			return customer.matches(filterString);
		}

		public boolean appliesToProperty(Object propertyId) {
			return false;
		}
	}

}
