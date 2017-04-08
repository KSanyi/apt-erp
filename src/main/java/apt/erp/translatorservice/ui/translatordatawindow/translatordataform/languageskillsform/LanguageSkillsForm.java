package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import java.util.Arrays;
import java.util.List;

import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.common.vaadin.OptionalTextFieldWidget;
import apt.erp.translatorservice.domain.LanguageSkills;

@SuppressWarnings("serial")
public class LanguageSkillsForm extends VerticalLayout {

    private final LanguageServicesForm languageServicesForm;
    
    private final TopicsSelectorForm topicsSelectorForm;
    
    private final OptionalTextFieldWidget interpeterIdField;
    
    private final OptionalTextFieldWidget translatorIdField;

	private final List<Field<?>> dataFields;
	
	public LanguageSkillsForm(LanguageSkills languageSkills) {
		
		languageServicesForm = new LanguageServicesForm(languageSkills.services());
		topicsSelectorForm = new TopicsSelectorForm(languageSkills.subTopics());
		interpeterIdField = new OptionalTextFieldWidget("Tolmács igazolvány", "Igazolvány száma:", languageSkills.interpeterId);
		translatorIdField = new OptionalTextFieldWidget("Szakfordító igazolvány", "Igazolvány száma:", languageSkills.translatorId);
		dataFields = Arrays.asList(interpeterIdField, translatorIdField);
		
		bindData(languageSkills);
		createValidators();

		createLayout();
	}
	
	
	private void bindData(LanguageSkills languageSkills) {
		
	}
	
	private void createValidators() {
	}
	
	private void createLayout() {
	    setSpacing(true);
	    setMargin(true);
	    setSizeFull();
	    
	    HorizontalLayout bottomLayout = LayoutFactory.createHorizontalLayout(topicsSelectorForm, interpeterIdField, translatorIdField);
	    
	    addComponents(languageServicesForm, bottomLayout);
	}
	
	public boolean isDataModified() {
		return dataFields.stream().anyMatch(Field::isModified) || languageServicesForm.isDataModified() || topicsSelectorForm.isDataModified();
	}
	
	public boolean isDataValid() {
		return dataFields.stream().allMatch(Field::isValid) && languageServicesForm.isDataValid() && languageServicesForm.isDataValid() ;
	}
	
	public LanguageSkills getLanguageSkills() {
		return new LanguageSkills(languageServicesForm.getLanguageServices(), topicsSelectorForm.getSubTopics(), 
				interpeterIdField.getOptionalValue(), translatorIdField.getOptionalValue());
	}
	
}
