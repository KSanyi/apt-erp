package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.projectservice.domain.Language;

@SuppressWarnings("serial")
public class ServiceForm extends HorizontalLayout {

    private ComboBox languageFrom = FormFieldFactory.createComboBox("", Language.all);
    private ComboBox languageTo = FormFieldFactory.createComboBox("", Language.all);
    private CheckBox translateCheck = new CheckBox("Fordítás");
    private CheckBox interpretCheck = new CheckBox("Tolmácskodás");
    private CheckBox lectorCheck = new CheckBox("Lektorálás");
    
    ServiceForm() {
        createLayout();
    }
    
    private void createLayout() {
        
        setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        setSpacing(true);
        languageFrom.setWidth("110px");
        languageFrom.setValue(Language.Hungarian);
        languageTo.setWidth("110px");
        languageTo.setValue(Language.English);
        
        Label arrow = new Label(FontAwesome.ARROW_RIGHT.getHtml(), ContentMode.HTML);
        addComponents(languageFrom, arrow, languageTo, translateCheck, interpretCheck, lectorCheck);
        setComponentAlignment(languageFrom, Alignment.MIDDLE_CENTER);
        setComponentAlignment(languageTo, Alignment.MIDDLE_CENTER);
    }
    
}
