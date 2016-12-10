package apt.erp.translatorservice.ui.translatordatawindow.translatordataform;

import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.translatorservice.domain.Translator;
import apt.erp.translatorservice.ui.translatordatawindow.translatordataform.invoicingdataform.InvoicingDataForm;

@SuppressWarnings("serial")
public class TranslatorDataForm extends TabSheet {

    private final ContactDataForm contactDataForm;
    private final InvoicingDataForm invoicingDataForm;
    
    private final Translator translator;
    
    public TranslatorDataForm(Translator translator, ZipTownMap zipTownMap) {
        
        this.translator = translator;
        
        contactDataForm = new ContactDataForm(translator.contactData);
        invoicingDataForm = new InvoicingDataForm(translator.invoicingData, zipTownMap);
        
        addTab(contactDataForm, "Kontakt adatok");
        addTab(invoicingDataForm, "Számlázási adatok");
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
