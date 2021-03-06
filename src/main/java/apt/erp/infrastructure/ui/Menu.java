package apt.erp.infrastructure.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class Menu extends MenuBar {

	private static Logger logger = LoggerFactory.getLogger(Menu.class);
	
	public Menu() {
		
	    setStyleName(ValoTheme.MENUBAR_SMALL);
		setSizeFull();
		
		MenuItem customersMenuItem = addItem("Törzsadatok", null);
		
		customersMenuItem.addItem("Ügyfelek", new LoggerCommand(c -> ErpUI.getCurrent().openCustomersListWindow()));
		customersMenuItem.addItem("Fordítók", new LoggerCommand(c -> ErpUI.getCurrent().openTranslatorsListWindow()));
		
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

