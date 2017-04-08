package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.projectservice.domain.LanguageService;

@SuppressWarnings("serial")
public class LanguageServicesForm extends Panel {

	private final Layout serviceFormsLayout = new VerticalLayout();
	
	private final List<LanguageServiceForm> serviceForms = new ArrayList<>();
    
    private final Button addServiceButton = new Button("Új nyelvpár");
    
    private boolean addOrRemoveButtonWasCicked = false;
    
    public LanguageServicesForm(List<LanguageService> services) {
    	bindData(services);
		createValidators();

		addServiceButton.addClickListener(click -> addServiceForm(LanguageServiceFormData.createEmpty()));
		
		createLayout();
	}

	private void createLayout() {
		setCaption("Szállítói szolgáltatások");
	    addServiceButton.addStyleName(ValoTheme.BUTTON_TINY);
	    addServiceButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    
	    setContent(LayoutFactory.createCenteredVerticalLayout(serviceFormsLayout, addServiceButton));
	}

	private void bindData(List<LanguageService> services) {
		LanguageServiceFormData.createServiceFormDatas(services).stream().forEach(this::addServiceForm);
	}
	
	private void createValidators() {
		// TODO Auto-generated method stub
		
	}
	
	private void addServiceForm(LanguageServiceFormData serviceFormData) {
	    LanguageServiceForm serviceForm = new LanguageServiceForm(serviceFormData);
	    Button removeButton = new Button(FontAwesome.CLOSE);
	    removeButton.addStyleName(ValoTheme.BUTTON_TINY);
	    removeButton.addStyleName(ValoTheme.BUTTON_DANGER);
        
	    serviceForms.add(serviceForm);
	    HorizontalLayout layout = new HorizontalLayout(removeButton, serviceForm);
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
	
	public boolean isDataModified() {
		return serviceForms.stream().anyMatch(LanguageServiceForm::isDataModified) || addOrRemoveButtonWasCicked;
	}
	
	public boolean isDataValid() {
		boolean formsAreValid = serviceForms.stream().allMatch(LanguageServiceForm::isValid);
		return formsAreValid;
	}
	
	public List<LanguageService> getLanguageServices() {
		return LanguageServiceFormData.createLanguageServices(serviceForms.stream().map(LanguageServiceForm::getServiceFormData).collect(Collectors.toList()));
	}
}
