package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.projectservice.domain.Language;

@SuppressWarnings("serial")
public class LanguageServiceForm extends CustomField<LanguageServiceFormData> {

	private ComboBox<Language> languageFrom = FormFieldFactory.createEnumComboBox("", Language.class);
	private ComboBox<Language> languageTo = FormFieldFactory.createEnumComboBox("", Language.class);
	private CheckBox translateCheck = new CheckBox("Fordítás");
	private CheckBox interpretCheck = new CheckBox("Tolmácskodás");
	private CheckBox lectorCheck = new CheckBox("Lektorálás");

	LanguageServiceForm(LanguageServiceFormData serviceFormData) {
		bindData(serviceFormData);
		createValidator();
	}

	private void bindData(LanguageServiceFormData serviceFormData) {
		languageFrom.setValue(serviceFormData.languageFrom);
		languageTo.setValue(serviceFormData.languageTo);

		translateCheck.setValue(serviceFormData.translation);
		interpretCheck.setValue(serviceFormData.interpretation);
		lectorCheck.setValue(serviceFormData.lectoring);
	}

	private void createValidator() {
		ValueChangeListener<Boolean> doNotAllowEmptySelectionListener = new ValueChangeListener<Boolean>() {
			@Override
			public void valueChange(ValueChangeEvent<Boolean> event) {
				if(!(translateCheck.getValue() || interpretCheck.getValue() || lectorCheck.getValue())){
					translateCheck.setValue(true);
				}
			}
		};
		
		translateCheck.addValueChangeListener(doNotAllowEmptySelectionListener);
		interpretCheck.addValueChangeListener(doNotAllowEmptySelectionListener);
		lectorCheck.addValueChangeListener(doNotAllowEmptySelectionListener);
	}

	@Override
	protected Component initContent() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
		layout.setSpacing(true);
		languageFrom.setWidth("110px");
		languageFrom.setStyleName(ValoTheme.COMBOBOX_TINY);
		languageTo.setWidth("110px");
		languageTo.setStyleName(ValoTheme.COMBOBOX_TINY);

		Label arrow = new Label(VaadinIcons.ARROW_RIGHT.getHtml(), ContentMode.HTML);
		layout.addComponents(languageFrom, arrow, languageTo, translateCheck, interpretCheck, lectorCheck);
		layout.setComponentAlignment(languageFrom, Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(languageTo, Alignment.MIDDLE_CENTER);
		return layout;
	}

	@Override
	public LanguageServiceFormData getValue() {
		return new LanguageServiceFormData((Language) languageFrom.getValue(), (Language) languageTo.getValue(),
				translateCheck.getValue(), interpretCheck.getValue(), lectorCheck.getValue());
	}

	@Override
	protected void doSetValue(LanguageServiceFormData value) {
		throw new UnsupportedOperationException();
	}

}
