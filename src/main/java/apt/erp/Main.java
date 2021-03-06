package apt.erp;

import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.customerservice.application.DemoCustomerRepository;
import apt.erp.infrastructure.server.ApplicationService;
import apt.erp.infrastructure.server.ErpServer;
import apt.erp.translatorservice.application.DemoTranslatorRepository;
import apt.erp.userservice.application.DemoAuthenticator;

public class Main {

	public static void main(String[] args) throws Exception {

	    int port = getPort();
	    
	    ApplicationService applicationService = new ApplicationService(
	    		new DemoAuthenticator(),
	            new DemoCustomerRepository(100), 
	            new DemoTranslatorRepository(50),
	            new ZipTownMap());

        new ErpServer(port, applicationService).startServer();
	}
	
	private static int getPort() {
	    String port = System.getenv("PORT");
	    if(port == null) {
	        throw new IllegalArgumentException("System environment variable PORT is missing");
	    }
	    
	    try{
	        return Integer.parseInt(port);
	    } catch(NumberFormatException ex) {
	        throw new IllegalArgumentException("Illegal system environment variable PORT: " + port);	        
	    }
	    
	}

}
