package apt.erp.translatorservice.ui.translatordatawindow.translatordataform;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.translatorservice.domain.Translator;
import apt.erp.translatorservice.ui.translatordatawindow.translatordataform.documentsform.DocumentsForm;
import apt.erp.translatorservice.ui.translatordatawindow.translatordataform.invoicingdataform.InvoicingDataForm;
import apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform.LanguageSkillsForm;

@SuppressWarnings("serial")
public class TranslatorDataForm extends CustomField<Translator> {

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
    }
    
	@Override
	public Translator getValue() {
		return translator.updated(personalDataForm.getValue(), invoicingDataForm.getValue(),
        		languageSkillsForm.getValue(), documentsForm.getDocuments(), translator.comment);
	}

	@Override
	protected Component initContent() {
		TabSheet tabsheet = new TabSheet();
		tabsheet.addTab(personalDataForm, "Személyes adatok");
		tabsheet.addTab(invoicingDataForm, "Számlázási adatok");
		tabsheet.addTab(languageSkillsForm, "Nyelvi képzettség");
		tabsheet.addTab(documentsForm, "Dokumentumok");
		tabsheet.addTab(new Label("Fejlesztés alatt"), "Árazás");
		tabsheet.addTab(new Label("Fejlesztés alatt"), "...");
        
		tabsheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabsheet.setSizeFull();
		tabsheet.setHeight("500");
        return tabsheet;
	}

	@Override
	protected void doSetValue(Translator value) {
		throw new UnsupportedOperationException();
	}

}
