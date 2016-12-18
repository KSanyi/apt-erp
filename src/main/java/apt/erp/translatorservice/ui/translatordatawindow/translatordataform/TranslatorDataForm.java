package apt.erp.translatorservice.ui.translatordatawindow.translatordataform;

import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.translatorservice.domain.Translator;
import apt.erp.translatorservice.ui.translatordatawindow.translatordataform.invoicingdataform.InvoicingDataForm;
import apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform.LanguageSkillsForm;

@SuppressWarnings("serial")
public class TranslatorDataForm extends TabSheet {

    private final ContactDataForm contactDataForm;
    private final InvoicingDataForm invoicingDataForm;
    private final LanguageSkillsForm languageSkillsForm;
    
    private final Translator translator;
    
    public TranslatorDataForm(Translator translator, ZipTownMap zipTownMap) {
        
        this.translator = translator;
        
        contactDataForm = new ContactDataForm(translator.contactData);
        invoicingDataForm = new InvoicingDataForm(translator.invoicingData, zipTownMap);
        languageSkillsForm = new LanguageSkillsForm(translator.languageSkills);
        
        addTab(contactDataForm, "Kontakt adatok");
        addTab(invoicingDataForm, "Számlázási adatok");
        addTab(languageSkillsForm, "Nyelvi képzettség");
        addTab(new Label("Fejlesztés alatt"), "Dokumentumok");
        addTab(new Label("Fejlesztés alatt"), "Árazás");
        addTab(new Label("Fejlesztés alatt"), "...");
        
        addStyleName(ValoTheme.TABSHEET_FRAMED);
        setSizeFull();
    }
    
    public Translator getTranslator() {
        return translator.updated(contactDataForm.getContactData(), invoicingDataForm.getInvoicingData(), translator.languages(),
                translator.languageSkills, translator.comment);
    }

    public boolean isDataValid() {
        return contactDataForm.isDataValid() && invoicingDataForm.isDataValid();
    }

    public boolean isDataModified() {
        return contactDataForm.isDataModified() || invoicingDataForm.isDataModified();
    }
    
}
