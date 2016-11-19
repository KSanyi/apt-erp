package apt.erp.infrastructure.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.MenuBar;

@SuppressWarnings("serial")
public class Menu extends MenuBar {

	private static Logger logger = LoggerFactory.getLogger(Menu.class);
	
	public Menu() {
		
		setSizeFull();
		
		MenuItem customersMenuItem = addItem("Customers", null);
		customersMenuItem.addItem("Customers List", new LoggerCommand(c -> ErpUI.getCurrent().openCustomersListWindow()));
		
	}
	
	private static class LoggerCommand implements Command {
		private final Command action;
		public LoggerCommand(Command action){
			this.action = action;
		}
		@Override
		public void menuSelected(MenuItem selectedItem) {
			logger.debug("Menuitem '" + selectedItem.getText() + "' was clicked");
			action.menuSelected(selectedItem);
		}
	}
	
}

