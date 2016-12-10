package apt.erp.infrastructure.server;

import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.customerservice.application.CustomerApplicationService;
import apt.erp.customerservice.domain.CustomerDataRepository;
import apt.erp.translatorservice.application.TranslatorApplicationService;
import apt.erp.translatorservice.domain.TranslatorRepository;

public class ApplicationService {

	public final CustomerApplicationService customerService;
	public final TranslatorApplicationService translatorService;
	
	public ApplicationService(
			CustomerDataRepository customerRepository, TranslatorRepository translatorRepository, ZipTownMap zipTownMap) {
		customerService = new CustomerApplicationService(customerRepository, zipTownMap);
		translatorService = new TranslatorApplicationService(translatorRepository, zipTownMap);
	}
	
}
