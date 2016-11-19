package apt.erp.infrastructure.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apt.erp.infrastructure.ui.ErpUI;

public class ErpServer extends Server {

	private static final Logger logger = LoggerFactory.getLogger(ErpServer.class);
	
	public ErpServer(int httpPort, ApplicationService applicationService) {
		super(httpPort);
		setHandler(createServletContextHandler(applicationService));
		logger.info("KITS-ERP Server initialised on port " + httpPort);
	}
	
	public void startServer() throws Exception {
		super.start();
		logger.info("Erp Server started");
		super.join();
	}
	
	private ServletContextHandler createServletContextHandler(ApplicationService applicationService) {
		ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		servletContextHandler.setClassLoader(Thread.currentThread().getContextClassLoader());
		servletContextHandler.addServlet(createServletHolder(applicationService), "/*");
		servletContextHandler.getSessionHandler().getSessionManager().setMaxInactiveInterval(300); // no timeout 
		return servletContextHandler;
	}
	
	private ServletHolder createServletHolder(ApplicationService applicationService) {
		ServletHolder servletHolder = new ServletHolder(new ErpServlet(applicationService));
		servletHolder.setInitParameter("UI", ErpUI.class.getCanonicalName());
		//servletHolder.setInitParameter("widgetset", "qcr.balancesheet.infrastructure.web.widgetset.BalanceSheetWidgetSet");
		return servletHolder;
	}

	
}
