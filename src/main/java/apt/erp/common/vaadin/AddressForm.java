package apt.erp.common.vaadin;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import apt.erp.common.domain.Address;

@SuppressWarnings("serial")
class AddressForm extends VerticalLayout {

	private final ZipTownMap zipTownMap;
	
	private final TextField zipField = FormFieldFactory.createFormTextField("Irányítószám", 60, true);
	private final TextField townField = FormFieldFactory.createFormTextField("Település", 200, true);
	private final TextField streetField = FormFieldFactory.createFormTextField("Utca", 200, true);
	private final TextField houseNumberField = FormFieldFactory.createFormTextField("Szám", 60, true);
	
	private final List<Field<?>> dataFields = Arrays.asList(zipField, townField, streetField, houseNumberField);
	
	public AddressForm(String caption, Address address, ZipTownMap zipTownMap) {
		this.zipTownMap = zipTownMap;
		
		bindData(address);
		
		initZipField();
		initTownField();
		
		createLayout();
	}
	
	private void bindData(Address address) {
		if(address != null) {
			zipField.setPropertyDataSource(new ObjectProperty<String>(address.zip));
			townField.setPropertyDataSource(new ObjectProperty<String>(address.town));
			streetField.setPropertyDataSource(new ObjectProperty<String>(address.street));
			houseNumberField.setPropertyDataSource(new ObjectProperty<String>(address.houseNumber));	
		}
	}
	
	private void initZipField() {
		zipField.addValidator(new RegexpValidator("\\d{4}", "Hibás irányítószám"));
		zipField.addValueChangeListener(e -> townField.setValue(zipTownMap.getTown(e.getProperty().getValue().toString())));
	}
	
	private void initTownField() {
		townField.addValidator(new HungarianNameValidator("Hibás településnév"));
	}

	boolean isDataModified() {
		return dataFields.stream().anyMatch(Field::isModified);
	}
	
	boolean isValid() {
		return dataFields.stream().allMatch(Field::isValid);
	}
	
	Address getAddress() {
		return new Address(zipField.getValue(), townField.getValue(), streetField.getValue(), houseNumberField.getValue());
	}
	
	private void createLayout() {
		setSpacing(true);
		setMargin(true);
		HorizontalLayout row1 = LayoutFactory.createHorizontalLayout(zipField, townField);
		HorizontalLayout row2 = LayoutFactory.createHorizontalLayout(streetField, houseNumberField);
		addComponents(row1, row2);
		row2.setSizeFull();
		townField.setSizeFull();
		streetField.setSizeFull();
		setSizeFull();
	}
	
}
