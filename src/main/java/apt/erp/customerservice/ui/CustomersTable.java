package apt.erp.customerservice.ui;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerService;
import apt.erp.customerservice.ui.customerdatawindow.UpdateCustomerDataWindow;

@SuppressWarnings("serial")
class CustomersTable extends Table {

	private final CustomerService customerService;
	
	public CustomersTable(CustomerService customerService, ZipTownMap zipTownMap) {
	    this.customerService = customerService;
		
		addContainerProperty("Name", String.class, null);
		addContainerProperty("Address", String.class, null);
		
		setColumnHeaders("Név", "Cím");

		setSortContainerPropertyId("Name");
		refresh();

		addStyleName(ValoTheme.TABLE_SMALL);
		setSizeFull();
		setSelectable(true);

		addItemClickListener(event -> {
			CustomerData customerData = (CustomerData)event.getItemId();
			UpdateCustomerDataWindow updateCustomerDataWindow = new UpdateCustomerDataWindow(customerService, customerData, zipTownMap);
			updateCustomerDataWindow.addCustomerChangeListener(c -> refresh());
			UI.getCurrent().addWindow(updateCustomerDataWindow);
		});
	}
	
	public void refresh() {
		
		Object selectedItem = getValue();
		int currentPageFirstItemIndex = getCurrentPageFirstItemIndex();
		
	    removeAllItems();
		for(CustomerData customerData : customerService.loadAllCustomers()) {
			addItem(new Object[]{customerData.name.toString(), customerData.address.toString()}, customerData);
		}
		sort();
		
		setValue(selectedItem, true);
		setCurrentPageFirstItemIndex(currentPageFirstItemIndex);
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
		    CustomerData customerData = (CustomerData)itemId;
		    return customerData.matches(filterString);
		}

		public boolean appliesToProperty(Object propertyId) {
			return false;
		}
	}

}
