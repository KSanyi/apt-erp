package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Field;
import com.vaadin.ui.VerticalLayout;

import apt.erp.translatorservice.domain.LanguageSkills;

@SuppressWarnings("serial")
public class LanguageSkillsForm extends VerticalLayout {

    private final LanguageServicesForm languageServicesForm;
    
    private final TopicsSelectorForm topicsSelectorForm;

	private final List<Field<?>> dataFields = new ArrayList<>();
	
	public LanguageSkillsForm(LanguageSkills languageSkills) {
		
		languageServicesForm = new LanguageServicesForm(languageSkills.services());
		topicsSelectorForm = new TopicsSelectorForm(languageSkills.subTopics());
		
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
	    
	    addComponents(languageServicesForm, topicsSelectorForm);
	}
	
	public boolean isDataModified() {
		return dataFields.stream().anyMatch(Field::isModified) || languageServicesForm.isDataModified() || topicsSelectorForm.isDataModified();
	}
	
	public boolean isDataValid() {
		return dataFields.stream().allMatch(Field::isValid) && languageServicesForm.isDataValid() && languageServicesForm.isDataValid();
	}
	
	public LanguageSkills getLanguageSkills() {
		return new LanguageSkills(languageServicesForm.getLanguageServices(), topicsSelectorForm.getSubTopics());
	}
	
}
