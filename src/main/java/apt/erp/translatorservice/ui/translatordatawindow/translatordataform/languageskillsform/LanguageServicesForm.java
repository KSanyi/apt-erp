package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.projectservice.domain.LanguageService;

@SuppressWarnings("serial")
public class LanguageServicesForm extends CustomField<List<LanguageService>> {

	private final Layout serviceFormsLayout = new VerticalLayout();
	
	private final List<LanguageServiceForm> serviceForms = new ArrayList<>();
    
    private final Button addServiceButton = new Button("Új nyelvpár");
    
    public LanguageServicesForm(List<LanguageService> services) {
    	bindData(services);

		addServiceButton.addClickListener(click -> addServiceForm(LanguageServiceFormData.createEmpty()));
	}

	private void bindData(List<LanguageService> services) {
		LanguageServiceFormData.createServiceFormDatas(services).stream().forEach(this::addServiceForm);
	}
	
	private void addServiceForm(LanguageServiceFormData serviceFormData) {
	    LanguageServiceForm serviceForm = new LanguageServiceForm(serviceFormData);
	    Button removeButton = new Button(VaadinIcons.CLOSE);
	    removeButton.addStyleName(ValoTheme.BUTTON_TINY);
	    removeButton.addStyleName(ValoTheme.BUTTON_DANGER);
        
	    serviceForms.add(serviceForm);
	    HorizontalLayout layout = new HorizontalLayout(removeButton, serviceForm);
	    layout.setSpacing(true);
	    layout.setComponentAlignment(removeButton, Alignment.BOTTOM_CENTER);
	    removeButton.addClickListener(click -> {
	    	serviceFormsLayout.removeComponent(layout);
	    	serviceForms.remove(serviceForm);
	    });
			
	    serviceFormsLayout.addComponent(layout);
	}
	
	@Override
	public List<LanguageService> getValue() {
		return LanguageServiceFormData.createLanguageServices(serviceForms.stream().map(LanguageServiceForm::getValue).collect(Collectors.toList()));
	}

	@Override
	protected Component initContent() {
		setCaption("Szállítói szolgáltatások");
	    addServiceButton.addStyleName(ValoTheme.BUTTON_TINY);
	    addServiceButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    
	    return LayoutFactory.createCenteredVerticalLayout(serviceFormsLayout, addServiceButton);
	}

	@Override
	protected void doSetValue(List<LanguageService> value) {
		throw new UnsupportedOperationException();
		
	}
}
