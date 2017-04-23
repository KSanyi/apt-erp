package apt.erp.translatorservice.ui.translatordatawindow;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.domain.ValidationError;
import apt.erp.common.vaadin.ConfirmationDialog;
import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.translatorservice.domain.Translator;
import apt.erp.translatorservice.domain.TranslatorService;
import apt.erp.translatorservice.ui.translatordatawindow.translatordataform.TranslatorDataForm;

@SuppressWarnings("serial")
public class UpdateTranslatorDataWindow extends Window {

	private final TranslatorService translatorService;
	private final Translator translator;
	
	private final TranslatorDataForm translatorDataForm;
	
	private final Button updateButton = FormFieldFactory.createFormButton("Frissítés", FontAwesome.SAVE, ValoTheme.BUTTON_PRIMARY, click -> updateTranslatorData());
	private final Button deleteButton = FormFieldFactory.createFormButton("Törlés", FontAwesome.REMOVE, ValoTheme.BUTTON_DANGER);
	
	private final List<TranslatorDataChangeListener> translatorDataChangeListeners = new ArrayList<>();
	
	public UpdateTranslatorDataWindow(TranslatorService translatorService, Translator translator, ZipTownMap zipTownMap) {
		this.translatorService = translatorService;
		this.translator = translator;
		setCaption("Translator Id: " + translator.id.value);
		
		translatorDataForm = new TranslatorDataForm(translator, zipTownMap);
		setContent(createLayout());
		
		center();
        setWidth("1000px");
        setHeight("500px");
        
        deleteButton.addClickListener(click -> {
        	ConfirmationDialog.show("Megerősítés", "Biztosan törli a fordítót?", this::deleteTranslator);
        });
	}
	
	private void updateTranslatorData() {
		try {
			translatorService.updateTranslator(translatorDataForm.getValue());
			Notification.show("Fordító adatok frissítve");
			notifyTranslatorDataChangeListeners();
			this.close();
		} catch (ValidationError ex) {
			Notification.show("Validációs hiba: " + ex.getMessage(), Notification.Type.WARNING_MESSAGE);
		}
	}
	
	private void deleteTranslator() {
		translatorService.deleteTranslator(translator.id);
		Notification.show("Fordító törölve");
		notifyTranslatorDataChangeListeners();
		this.close();
	}
	
	private Layout createLayout() {
		updateButton.setWidth("100px");
		deleteButton.setWidth("100px");
		HorizontalLayout buttonBar = new HorizontalLayout(updateButton, deleteButton);
		VerticalLayout layout = new VerticalLayout(translatorDataForm, buttonBar);
		layout.setSizeFull();
		layout.setComponentAlignment(buttonBar, Alignment.BOTTOM_CENTER);
		return layout;
	}
	
	public void addTranslatorDataChangeListener(TranslatorDataChangeListener translatorDataChangeListener) {
        translatorDataChangeListeners.add(translatorDataChangeListener);
    }
    
    private void notifyTranslatorDataChangeListeners() {
        translatorDataChangeListeners.stream().forEach(listener -> listener.notifyTranslatorDataChanged(translator));
    }

}
