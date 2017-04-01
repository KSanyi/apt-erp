package apt.erp.infrastructure.server;

import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.customerservice.application.CustomerApplicationService;
import apt.erp.customerservice.domain.CustomerDataRepository;
import apt.erp.translatorservice.application.TranslatorApplicationService;
import apt.erp.translatorservice.domain.TranslatorRepository;
import apt.erp.userservice.Authenticator;

public class ApplicationService {

	public final Authenticator authenticator;
	public final CustomerApplicationService customerService;
	public final TranslatorApplicationService translatorService;
	
	public ApplicationService(Authenticator authenticator,
			CustomerDataRepository customerRepository, TranslatorRepository translatorRepository, ZipTownMap zipTownMap) {
		this.authenticator = authenticator;
		customerService = new CustomerApplicationService(customerRepository, zipTownMap);
		translatorService = new TranslatorApplicationService(translatorRepository, zipTownMap);
	}
	
}
