package apt.erp.infrastructure.server;

import com.vaadin.server.VaadinServlet;

public class ErpServlet extends VaadinServlet {

	private static final long serialVersionUID = 1L;

	public final ApplicationService applicationService;
	
	public ErpServlet(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

}
