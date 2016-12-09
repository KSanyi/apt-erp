package apt.erp.translatorservice.ui.translatordatawindow.translatordataform;

import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.translatorservice.domain.Translator;

@SuppressWarnings("serial")
public class TranslatorDataForm extends TabSheet {

    private final ContactDataForm contactDataForm;
    
    private final Translator translator;
    
    public TranslatorDataForm(Translator translator) {
        
        this.translator = translator;
        
        contactDataForm = new ContactDataForm(translator.contactData);
        
        addTab(contactDataForm, "Kontakt adatok");
        addTab(new Label("Fejlesztés alatt"), "Számlázási adatok");
        addTab(new Label("Fejlesztés alatt"), "Nyelvi képzettség");
        addTab(new Label("Fejlesztés alatt"), "Dokumentumok");
        addTab(new Label("Fejlesztés alatt"), "Árazás");
        addTab(new Label("Fejlesztés alatt"), "...");
        
        addStyleName(ValoTheme.TABSHEET_FRAMED);
        setSizeFull();
    }
    
    public Translator getTranslator() {
        return translator.updated(contactDataForm.getContactData(), translator.invoicingData, translator.languages(),
                translator.services(), translator.domains(), translator.comment);
    }

    public boolean isDataValid() {
        return contactDataForm.isDataValid();
    }

    public boolean isDataModified() {
        return contactDataForm.isDataModified();
    }
    
}
