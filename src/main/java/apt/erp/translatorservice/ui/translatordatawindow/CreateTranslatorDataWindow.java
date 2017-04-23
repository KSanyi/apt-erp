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
import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.translatorservice.domain.Translator;
import apt.erp.translatorservice.domain.TranslatorService;
import apt.erp.translatorservice.ui.translatordatawindow.translatordataform.TranslatorDataForm;

@SuppressWarnings("serial")
public class CreateTranslatorDataWindow extends Window {

	private final TranslatorService translatorService;
	
	private final TranslatorDataForm translatorDataForm;
	
	private final Button saveButton = FormFieldFactory.createFormButton("Mentés", FontAwesome.SAVE, ValoTheme.BUTTON_PRIMARY, click -> createTranslatorData());
	
	private final List<TranslatorDataChangeListener> translatorDataChangeListeners = new ArrayList<>();
	
	public CreateTranslatorDataWindow(TranslatorService translatorService, ZipTownMap zipTownMap) {
		this.translatorService = translatorService;
		setCaption("Új fordító");
		
		translatorDataForm = new TranslatorDataForm(Translator.createEmpty(), zipTownMap);
		setContent(LayoutFactory.createCenteredVerticalLayout(translatorDataForm, saveButton));

		setPositionY(50);
		setPositionX(400);
		
		setWidth("1000px");
		setHeight("500px");
	}
	
	private void createTranslatorData() {
		Translator translator = translatorDataForm.getValue();
		translatorService.createTranslator(translator);
		Notification.show("Fodító létrehozva");
		notifyTranslatorDataChangeListeners(translator);
		this.close();
	}
	
	public void addTranslatorDataChangeListener(TranslatorDataChangeListener translatorDataChangeListener) {
	    translatorDataChangeListeners.add(translatorDataChangeListener);
	}
	
	private void notifyTranslatorDataChangeListeners(Translator translator) {
	    translatorDataChangeListeners.stream().forEach(listener -> listener.notifyTranslatorDataChanged(translator));
	}
	
}
	
