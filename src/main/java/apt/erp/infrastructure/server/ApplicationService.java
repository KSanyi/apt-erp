package apt.erp.infrastructure.server;

import apt.erp.customerservice.application.CustomerApplicationService;
import apt.erp.customerservice.domain.CustomerDataRepository;
import apt.erp.customerservice.web.customerform.ZipTownMap;

public class ApplicationService {

	public final CustomerApplicationService customerService;
	
	public ApplicationService(
			CustomerDataRepository customerRepository, ZipTownMap zipTownMap) {
		customerService = new CustomerApplicationService(customerRepository, zipTownMap);
	}
	
}
