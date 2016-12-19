package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.projectservice.domain.LanguageService;
import apt.erp.translatorservice.domain.LanguageSkills;

@SuppressWarnings("serial")
public class LanguageSkillsForm extends VerticalLayout {

    private final Layout serviceFormsLayout = new VerticalLayout();

    private final List<LanguageServiceForm> serviceForms = new ArrayList<>();
    
    private final Button addServiceButton = new Button("Új nyelvi készség");
    
    private boolean addOrRemoveButtonWasCicked = false;
    
	private final List<Field<?>> dataFields = new ArrayList<>();
	
	public LanguageSkillsForm(LanguageSkills languageSkills) {
		bindData(languageSkills);
		createValidators();

		addServiceButton.addClickListener(click -> addServiceForm(LanguageServiceFormData.createEmpty()));
		
		createLayout();
	}
	
	private void addServiceForm(LanguageServiceFormData serviceFormData) {
	    LanguageServiceForm serviceForm = new LanguageServiceForm(serviceFormData);
	    Button removeButton = new Button(FontAwesome.CLOSE);
	    removeButton.addStyleName(ValoTheme.BUTTON_TINY);
	    removeButton.addStyleName(ValoTheme.BUTTON_DANGER);
        
	    serviceForms.add(serviceForm);
	    HorizontalLayout layout = new HorizontalLayout(serviceForm, removeButton);
	    layout.setSpacing(true);
	    layout.setComponentAlignment(removeButton, Alignment.BOTTOM_CENTER);
	    removeButton.addClickListener(click -> {
	    	serviceFormsLayout.removeComponent(layout);
	    	serviceForms.remove(serviceForm);
	    	addOrRemoveButtonWasCicked = true;
	    });
			
	    serviceFormsLayout.addComponent(layout);
	    addOrRemoveButtonWasCicked = true;
	}
	
	private void bindData(LanguageSkills languageSkills) {
		LanguageServiceFormData.createServiceFormDatas(languageSkills.services()).stream().forEach(this::addServiceForm);
	}
	
	private void createValidators() {
	}
	
	private void createLayout() {
	    setSpacing(true);
	    setMargin(true);
	    setSizeFull();
	    
	    addServiceButton.addStyleName(ValoTheme.BUTTON_TINY);
	    addServiceButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    
	    addComponents(serviceFormsLayout, addServiceButton);
	    setComponentAlignment(addServiceButton, Alignment.MIDDLE_CENTER);
	}
	
	public boolean isDataModified() {
		return dataFields.stream().anyMatch(Field::isModified) || serviceForms.stream().anyMatch(LanguageServiceForm::isDataModified) || addOrRemoveButtonWasCicked;
	}
	
	public boolean isDataValid() {
		return dataFields.stream().allMatch(Field::isValid) && serviceForms.stream().allMatch(LanguageServiceForm::isDataValid);
	}
	
	public LanguageSkills getLanguageSkills() {
		List<LanguageService> languageServices = LanguageServiceFormData.createLanguageServices(serviceForms.stream().map(LanguageServiceForm::getServiceFormData).collect(Collectors.toList()));
		return new LanguageSkills(languageServices);
	}
	
}
