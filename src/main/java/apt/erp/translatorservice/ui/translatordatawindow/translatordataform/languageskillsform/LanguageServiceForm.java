package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.projectservice.domain.Language;

@SuppressWarnings("serial")
public class LanguageServiceForm extends HorizontalLayout {

    private ComboBox languageForm = FormFieldFactory.createEnumComboBox("", Language.class);
    private ComboBox languageTo = FormFieldFactory.createEnumComboBox("", Language.class);
    private CheckBox translateCheck = new CheckBox("Fordítás");
    private CheckBox interpretCheck = new CheckBox("Tolmácskodás");
    private CheckBox lectorCheck = new CheckBox("Lektorálás");
    
    private final List<Field<?>> dataFields = Arrays.asList(languageForm, languageTo, translateCheck, interpretCheck, lectorCheck);
    
    LanguageServiceForm(LanguageServiceFormData serviceFormData) {
    	bindData(serviceFormData);
    	createLayout();
    }
    
    private void bindData(LanguageServiceFormData serviceFormData) {
    	languageForm.setPropertyDataSource(new ObjectProperty<>(serviceFormData.languageFrom));
    	languageTo.setPropertyDataSource(new ObjectProperty<>(serviceFormData.languageTo));
    	
    	translateCheck.setPropertyDataSource(new ObjectProperty<>(serviceFormData.translation));
    	translateCheck.setBuffered(true);
    	interpretCheck.setPropertyDataSource(new ObjectProperty<>(serviceFormData.interpretation));
    	interpretCheck.setBuffered(true);
    	lectorCheck.setPropertyDataSource(new ObjectProperty<>(serviceFormData.lectoring));
    	lectorCheck.setBuffered(true);
    }
    
    private void createLayout() {
        setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        setSpacing(true);
        languageForm.setWidth("110px");
        languageForm.setStyleName(ValoTheme.COMBOBOX_TINY);
        languageTo.setWidth("110px");
        languageTo.setStyleName(ValoTheme.COMBOBOX_TINY);
        
        Label arrow = new Label(FontAwesome.ARROW_RIGHT.getHtml(), ContentMode.HTML);
        addComponents(languageForm, arrow, languageTo, translateCheck, interpretCheck, lectorCheck);
        setComponentAlignment(languageForm, Alignment.MIDDLE_CENTER);
        setComponentAlignment(languageTo, Alignment.MIDDLE_CENTER);
    }
    
    boolean isDataModified() {
		return dataFields.stream().anyMatch(Field::isModified);
	}
	
	boolean isDataValid() {
		boolean someServiceCheck = translateCheck.getValue() && interpretCheck.getValue() && lectorCheck.getValue();
		boolean differentSourceAndDestLanguage = !languageForm.getValue().equals(languageTo.getValue());
		return someServiceCheck && differentSourceAndDestLanguage;
	}
	
	LanguageServiceFormData getServiceFormData() {
		return new LanguageServiceFormData((Language)languageForm.getValue(), (Language)languageTo.getValue(), translateCheck.getValue(), interpretCheck.getValue(), lectorCheck.getValue());
	}
    
}
