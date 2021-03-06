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

@Theme("kits-apt")
public class ErpUI extends UI {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(ErpUI.class);
	
	private ApplicationService applicationService = ((ErpServlet)VaadinServlet.getCurrent()).applicationService;

	private User user;
	
	@Override
	protected void init(VaadinRequest request) {

        logger.debug("ErpUI initialized");
        
        if(user == null) {
            showLogin();
        }
	}
	
	private void showLogin() {
	    LoginWindow loginWindow = new LoginWindow(applicationService.authenticator, this::buildUI);
	    addWindow(loginWindow);
	}
	
	private void buildUI(User user) {
		logger.info("User " + user.name + " logged in");
		setContent(new VerticalLayout(new Header(user), new Menu()));
	}
	
	public static ErpUI getCurrent() {
		return (ErpUI)UI.getCurrent();
	}
	
	public void openCustomersListWindow() {
		addWindow(applicationService.customerService.getCustomersWindow());
	}

    public void openTranslatorsListWindow() {
        addWindow(applicationService.translatorService.getTranslatorsWindow());
    }
	
}