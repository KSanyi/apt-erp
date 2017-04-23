package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.common.vaadin.OptionalTextFieldWidget;
import apt.erp.translatorservice.domain.LanguageSkills;

@SuppressWarnings("serial")
public class LanguageSkillsForm extends CustomField<LanguageSkills> {

    private final LanguageServicesForm languageServicesForm;
    
    private final TopicsSelectorForm topicsSelectorForm;
    
    private final OptionalTextFieldWidget interpeterIdField;
    
    private final OptionalTextFieldWidget translatorIdField;

	public LanguageSkillsForm(LanguageSkills languageSkills) {
		
		languageServicesForm = new LanguageServicesForm(languageSkills.services());
		topicsSelectorForm = new TopicsSelectorForm(languageSkills.subTopics());
		interpeterIdField = new OptionalTextFieldWidget("Tolmács igazolvány", "Igazolvány száma:", languageSkills.interpeterId);
		translatorIdField = new OptionalTextFieldWidget("Szakfordító igazolvány", "Igazolvány száma:", languageSkills.translatorId);
	}
	
	@Override
	public LanguageSkills getValue() {
		return new LanguageSkills(languageServicesForm.getValue(), topicsSelectorForm.getValue(), 
				interpeterIdField.getValue(), translatorIdField.getValue());
	}

	@Override
	protected Component initContent() {
		HorizontalLayout bottomLayout = LayoutFactory.createHorizontalLayout(topicsSelectorForm, interpeterIdField, translatorIdField);
		return new VerticalLayout(languageServicesForm, bottomLayout);
	}

	@Override
	protected void doSetValue(LanguageSkills value) {
		throw new UnsupportedOperationException();
	}
	
}
