package apt.erp.customerservice.application;

import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.customerservice.domain.CustomerDataRepository;
import apt.erp.customerservice.domain.CustomerService;
import apt.erp.customerservice.ui.CustomersWindow;

public class CustomerApplicationService {

	private final CustomerService customerService;
	private final ZipTownMap zipTownMap;
	
	public CustomersWindow getCustomersWindow() {
		return new CustomersWindow(customerService, zipTownMap);
	}
	
	public CustomerApplicationService(CustomerDataRepository customerRepository, ZipTownMap zipTownMap) {
		customerService = new CustomerService(customerRepository);
		this.zipTownMap = zipTownMap;
	}
	
}
