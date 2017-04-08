package apt.erp.translatorservice.ui.translatordatawindow.translatordataform;

import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.translatorservice.domain.Translator;
import apt.erp.translatorservice.ui.translatordatawindow.translatordataform.documentsform.DocumentsForm;
import apt.erp.translatorservice.ui.translatordatawindow.translatordataform.invoicingdataform.InvoicingDataForm;
import apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform.LanguageSkillsForm;

@SuppressWarnings("serial")
public class TranslatorDataForm extends TabSheet {

    private final PersonalDataForm personalDataForm;
    private final InvoicingDataForm invoicingDataForm;
    private final LanguageSkillsForm languageSkillsForm;
    private final DocumentsForm documentsForm;
    
    private final Translator translator;
    
    public TranslatorDataForm(Translator translator, ZipTownMap zipTownMap) {
        
        this.translator = translator;
        
        personalDataForm = new PersonalDataForm(translator.personalData);
        invoicingDataForm = new InvoicingDataForm(translator.invoicingData, zipTownMap);
        languageSkillsForm = new LanguageSkillsForm(translator.languageSkills);
        documentsForm = new DocumentsForm(translator.documents());
        
        addTab(personalDataForm, "Személyes adatok");
        addTab(invoicingDataForm, "Számlázási adatok");
        addTab(languageSkillsForm, "Nyelvi képzettség");
        addTab(documentsForm, "Dokumentumok");
        addTab(new Label("Fejlesztés alatt"), "Árazás");
        addTab(new Label("Fejlesztés alatt"), "...");
        
        addStyleName(ValoTheme.TABSHEET_FRAMED);
        setSizeFull();
    }
    
    public Translator getTranslator() {
        return translator.updated(personalDataForm.getPersonalData(), invoicingDataForm.getInvoicingData(),
        		languageSkillsForm.getLanguageSkills(), documentsForm.getDocuments(), translator.comment);
    }

    public boolean isDataValid() {
        return personalDataForm.isDataValid() && invoicingDataForm.isDataValid() && languageSkillsForm.isDataValid();
    }

    public boolean isDataModified() {
        return personalDataForm.isDataModified() || invoicingDataForm.isDataModified() || languageSkillsForm.isDataModified();
    }
    
}
