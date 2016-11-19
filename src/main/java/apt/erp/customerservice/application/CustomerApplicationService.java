package apt.erp.customerservice.application;

import apt.erp.customerservice.domain.CustomerRepository;
import apt.erp.customerservice.domain.CustomerService;
import apt.erp.customerservice.web.CustomersWindow;
import apt.erp.customerservice.web.customerform.ZipTownMap;

public class CustomerApplicationService {

	private final CustomerService customerService;
	private final ZipTownMap zipTownMap;
	
	public CustomersWindow getCustomersWindow() {
		return new CustomersWindow(customerService, zipTownMap);
	}
	
	public CustomerApplicationService(CustomerRepository customerRepository, ZipTownMap zipTownMap) {
		customerService = new CustomerService(customerRepository);
		this.zipTownMap = zipTownMap;
	}
	
}
