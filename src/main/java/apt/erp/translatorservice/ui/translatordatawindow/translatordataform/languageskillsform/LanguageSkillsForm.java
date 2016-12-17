package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class LanguageSkillsForm extends VerticalLayout {

    private final Layout serviceFormsLayout = new VerticalLayout();
	
    private Button addServiceButton = new Button("Új nyelvi készség");
    
	private final List<Field<?>> dataFields = new ArrayList<>();
	
	public LanguageSkillsForm() {

		bindData();
		createValidators();

		addServiceButton.addClickListener(click -> addServiceForm());
		
		createLayout();
	}
	
	private void addServiceForm() {
	    ServiceForm serviceForm = new ServiceForm();
	    Button removeButton = new Button(FontAwesome.CLOSE);
	    removeButton.addStyleName(ValoTheme.BUTTON_TINY);
	    removeButton.addStyleName(ValoTheme.BUTTON_DANGER);
        
	    HorizontalLayout layout = new HorizontalLayout(serviceForm, removeButton);
	    layout.setSpacing(true);
	    layout.setComponentAlignment(removeButton, Alignment.BOTTOM_CENTER);
	    removeButton.addClickListener(click -> serviceFormsLayout.removeComponent(layout));
	    serviceFormsLayout.addComponent(layout);
	}
	
	private void bindData() {
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
		return dataFields.stream().anyMatch(Field::isModified);
	}
	
	public boolean isDataValid() {
		return dataFields.stream().allMatch(Field::isValid);
	}

}
