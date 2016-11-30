package apt.erp.translatorservice.ui;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.translatorservice.domain.TranslatorService;
import apt.erp.translatorservice.ui.translatordatawindow.CreateTranslatorDataWindow;

@SuppressWarnings("serial")
public class TranslatorsWindow extends Window {

	public TranslatorsWindow(TranslatorService translatorService) {
		super("Translators");
		center();
		setWidth("500px");
		
		TranslatorsTable translatorsTable = new TranslatorsTable(translatorService);

		TextField filter = new TextField("Filter");
		filter.setStyleName(ValoTheme.TEXTFIELD_SMALL);
		filter.addTextChangeListener(textChangeEvent -> translatorsTable.filter(textChangeEvent.getText()));
		
		Button addButton = FormFieldFactory.createFormButton("Add translator", FontAwesome.PLUS, ValoTheme.BUTTON_PRIMARY);
		
		addButton.addClickListener(e -> {
		    CreateTranslatorDataWindow createTranslatorDataWindow = new CreateTranslatorDataWindow(translatorService);
		    createTranslatorDataWindow.addTranslatorDataChangeListener(t -> translatorsTable.refresh());
			UI.getCurrent().addWindow(createTranslatorDataWindow);
		});
		
		VerticalLayout layout = LayoutFactory.createCenteredVerticalLayout(filter, translatorsTable, addButton);
		layout.setComponentAlignment(filter, Alignment.TOP_CENTER);
		layout.setComponentAlignment(addButton, Alignment.BOTTOM_CENTER);
		setContent(layout);
	}
	
}
