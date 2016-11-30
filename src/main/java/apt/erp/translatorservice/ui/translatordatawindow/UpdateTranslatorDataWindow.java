package apt.erp.translatorservice.ui.translatordatawindow;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.domain.ValidationError;
import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.translatorservice.domain.Translator;
import apt.erp.translatorservice.domain.TranslatorService;
import apt.erp.translatorservice.ui.translatordatawindow.translatordataform.TranslatorDataForm;

@SuppressWarnings("serial")
public class UpdateTranslatorDataWindow extends Window {

	private final TranslatorService translatorService;
	private final Translator translator;
	
	private final TranslatorDataForm translatorDataForm;
	
	private final Button updateButton = FormFieldFactory.createFormButton("Update", FontAwesome.SAVE, ValoTheme.BUTTON_PRIMARY, click -> updateTranslatorData());
	private final Button deleteButton = FormFieldFactory.createFormButton("Delete", FontAwesome.REMOVE, ValoTheme.BUTTON_DANGER, click -> deleteTranslator());
	
	private final List<TranslatorDataChangeListener> translatorDataChangeListeners = new ArrayList<>();
	
	public UpdateTranslatorDataWindow(TranslatorService translatorService, Translator translator) {
		this.translatorService = translatorService;
		this.translator = translator;
		setCaption("Translator Id: " + translator.id.value);
		
		translatorDataForm = new TranslatorDataForm(translator);
		createLayout();
		
		center();
	}
	
	private void updateTranslatorData() {
		if(translatorDataForm.isDataModified()) {
			if(translatorDataForm.isDataValid()) {
				try {
					translatorService.updateTranslator(translatorDataForm.getTranslator());
					Notification.show("Translator has been updated");
					notifyTranslatorDataChangeListeners();
					this.close();
				} catch (ValidationError ex) {
					Notification.show("Validation error: " + ex.getMessage(), Notification.Type.WARNING_MESSAGE);
				}
			} else {
				Notification.show("Fix data errors", Notification.Type.WARNING_MESSAGE);
			}
		} else {
			this.close();
		}
	}
	
	private void deleteTranslator() {
		translatorService.deleteTranslator(translator.id);
		Notification.show("Translator has been deleted");
		notifyTranslatorDataChangeListeners();
		this.close();
	}
	
	private void createLayout() {
		HorizontalLayout buttonsLayout = LayoutFactory.createHorizontalLayout(updateButton, deleteButton);
		VerticalLayout layout = LayoutFactory.createCenteredVerticalLayout(translatorDataForm, buttonsLayout);
		setContent(layout);
		setWidth("700px");
	}
	
	public void addTranslatorDataChangeListener(TranslatorDataChangeListener translatorDataChangeListener) {
        translatorDataChangeListeners.add(translatorDataChangeListener);
    }
    
    private void notifyTranslatorDataChangeListeners() {
        translatorDataChangeListeners.stream().forEach(listener -> listener.notifyTranslatorDataChanged(translator));
    }

}
