package apt.erp;

import apt.erp.customerservice.application.DemoCustomerRepository;
import apt.erp.customerservice.web.customerform.ZipTownMap;
import apt.erp.infrastructure.server.ApplicationService;
import apt.erp.infrastructure.server.ErpServer;

public class Main {

	public static void main(String[] args) throws Exception {

	    ApplicationService applicationService = new ApplicationService(
                new DemoCustomerRepository(10000), new ZipTownMap());
        
        new ErpServer(8888, applicationService).startServer();
	}

}
