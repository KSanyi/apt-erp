package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.projectservice.domain.Language;

@SuppressWarnings("serial")
public class LanguageServiceForm extends CustomField<LanguageServiceFormData> {

	private ComboBox languageFrom = FormFieldFactory.createEnumComboBox("", Language.class);
	private ComboBox languageTo = FormFieldFactory.createEnumComboBox("", Language.class);
	private CheckBox translateCheck = new CheckBox("Fordítás");
	private CheckBox interpretCheck = new CheckBox("Tolmácskodás");
	private CheckBox lectorCheck = new CheckBox("Lektorálás");

	private final List<Field<?>> dataFields = Arrays.asList(languageFrom, languageTo, translateCheck, interpretCheck,
			lectorCheck);

	LanguageServiceForm(LanguageServiceFormData serviceFormData) {
		bindData(serviceFormData);
		createLayout();
		createValidator();
	}

	private void bindData(LanguageServiceFormData serviceFormData) {
		languageFrom.setPropertyDataSource(new ObjectProperty<>(serviceFormData.languageFrom));
		languageTo.setPropertyDataSource(new ObjectProperty<>(serviceFormData.languageTo));

		translateCheck.setPropertyDataSource(new ObjectProperty<>(serviceFormData.translation));
		translateCheck.setBuffered(true);
		interpretCheck.setPropertyDataSource(new ObjectProperty<>(serviceFormData.interpretation));
		interpretCheck.setBuffered(true);
		lectorCheck.setPropertyDataSource(new ObjectProperty<>(serviceFormData.lectoring));
		lectorCheck.setBuffered(true);
	}

	private void createLayout() {
		
	}

	private void createValidator() {
		ValueChangeListener doNotAllowEmptySelectionListener = new ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				if(!(translateCheck.getValue() || interpretCheck.getValue() || lectorCheck.getValue())){
					translateCheck.setValue(true);
				}
			}
		};
		
		translateCheck.addValueChangeListener(doNotAllowEmptySelectionListener);
		interpretCheck.addValueChangeListener(doNotAllowEmptySelectionListener);
		lectorCheck.addValueChangeListener(doNotAllowEmptySelectionListener);
	}

	boolean isDataModified() {
		return dataFields.stream().anyMatch(Field::isModified);
	}

	LanguageServiceFormData getServiceFormData() {
		return new LanguageServiceFormData((Language) languageFrom.getValue(), (Language) languageTo.getValue(),
				translateCheck.getValue(), interpretCheck.getValue(), lectorCheck.getValue());
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

		Label arrow = new Label(FontAwesome.ARROW_RIGHT.getHtml(), ContentMode.HTML);
		layout.addComponents(languageFrom, arrow, languageTo, translateCheck, interpretCheck, lectorCheck);
		layout.setComponentAlignment(languageFrom, Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(languageTo, Alignment.MIDDLE_CENTER);
		return layout;
	}

	@Override
	public Class<? extends LanguageServiceFormData> getType() {
		return LanguageServiceFormData.class;
	}

}
