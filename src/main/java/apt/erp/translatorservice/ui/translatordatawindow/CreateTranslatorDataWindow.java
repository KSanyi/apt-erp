package apt.erp.translatorservice.ui.translatordatawindow;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.translatorservice.domain.Translator;
import apt.erp.translatorservice.domain.TranslatorService;
import apt.erp.translatorservice.ui.translatordatawindow.translatordataform.TranslatorDataForm;

@SuppressWarnings("serial")
public class CreateTranslatorDataWindow extends Window {

	private final TranslatorService translatorService;
	
	private final TranslatorDataForm translatorDataForm;
	
	private final Button saveButton = FormFieldFactory.createFormButton("Create", FontAwesome.SAVE, ValoTheme.BUTTON_PRIMARY, click -> createTranslatorData());
	
	private final List<TranslatorDataChangeListener> translatorDataChangeListeners = new ArrayList<>();
	
	public CreateTranslatorDataWindow(TranslatorService translatorService) {
		this.translatorService = translatorService;
		setCaption("New Translator");
		
		translatorDataForm = new TranslatorDataForm(Translator.createEmpty());
		setContent(LayoutFactory.createCenteredVerticalLayout(translatorDataForm, saveButton));
		setWidth("400px");
		setHeight("600px");
		center();
	}
	
	private void createTranslatorData() {
		if(translatorDataForm.isDataValid()) {
			Translator translator = translatorDataForm.getTranslator();
			translatorService.createTranslator(translator);
			Notification.show("Translator has been created");
			notifyTranslatorDataChangeListeners(translator);
			this.close();
		} else {
			Notification.show("Fix data errors", Notification.Type.WARNING_MESSAGE);
		}
	}
	
	public void addTranslatorDataChangeListener(TranslatorDataChangeListener translatorDataChangeListener) {
	    translatorDataChangeListeners.add(translatorDataChangeListener);
	}
	
	private void notifyTranslatorDataChangeListeners(Translator translator) {
	    translatorDataChangeListeners.stream().forEach(listener -> listener.notifyTranslatorDataChanged(translator));
	}
	
}
	
