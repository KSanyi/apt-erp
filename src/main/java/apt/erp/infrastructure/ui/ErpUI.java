package apt.erp.infrastructure.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import apt.erp.infrastructure.server.ApplicationService;
import apt.erp.infrastructure.server.ErpServlet;
import apt.erp.userservice.User;

@Theme("kits-crm")
public class ErpUI extends UI {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(ErpUI.class);
	
	private ApplicationService applicationService = ((ErpServlet)VaadinServlet.getCurrent()).applicationService;

	@Override
	protected void init(VaadinRequest request) {

		User user = new User("ksanyi", "Kocso Sandor");
		
		buildUI(user);
		
        logger.debug("ErpUI initialized");
	}
	
	private void buildUI(User user) {
		setContent(new VerticalLayout(new Header(user), new Menu()));
	}
	
	public static ErpUI getCurrent() {
		return (ErpUI)UI.getCurrent();
	}
	
	public void openCustomersListWindow() {
		addWindow(applicationService.customerService.getCustomersWindow());
	}
	
}