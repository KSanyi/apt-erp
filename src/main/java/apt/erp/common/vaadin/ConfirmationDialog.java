package apt.erp.common.vaadin;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class ConfirmationDialog extends Window {

	public static void show(String caption, String message, Runnable action) {
		ConfirmationDialog dialog = new ConfirmationDialog(caption, message, action);
		UI.getCurrent().addWindow(dialog);
	}
	
	private ConfirmationDialog(String caption, String message, Runnable action) {
		
		setCaption(caption);
		
		Button yesButton = new Button("Igen");
		yesButton.addStyleName(ValoTheme.BUTTON_SMALL);
		yesButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		yesButton.setWidth("80px");
		yesButton.addClickListener(click -> {
			action.run();
			close();	
		});
		
		Button noButton = new Button("Nem");
		noButton.addStyleName(ValoTheme.BUTTON_SMALL);
		noButton.addStyleName(ValoTheme.BUTTON_DANGER);
		noButton.setWidth("80px");
		noButton.addClickListener(click -> close());
		
		Layout buttonBar = LayoutFactory.createHorizontalLayout(yesButton, noButton);
		
		setContent(LayoutFactory.createCenteredVerticalLayout(new Label(message), buttonBar));
		
		setModal(true);
		setResizable(false);
	}
	
}
