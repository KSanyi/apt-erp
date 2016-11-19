package apt.erp;

import apt.erp.customerservice.application.DemoCustomerRepository;
import apt.erp.customerservice.web.customerform.ZipTownMap;
import apt.erp.infrastructure.server.ApplicationService;
import apt.erp.infrastructure.server.ErpServer;

public class Main {

	public static void main(String[] args) throws Exception {

	    final int port = Integer.parseInt(System.getenv("PORT"));
	    
	    ApplicationService applicationService = new ApplicationService(new DemoCustomerRepository(10000), new ZipTownMap());

        new ErpServer(port, applicationService).startServer();
	}

}
