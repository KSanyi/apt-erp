package apt.erp.common.vaadin;

import java.util.Arrays;
import java.util.List;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import apt.erp.common.domain.Address;

@SuppressWarnings("serial")
class AddressForm extends CustomField<Address> {

	private final ZipTownMap zipTownMap;
	
	private final TextField zipField = FormFieldFactory.createFormTextField("Irányítószám", 60, true);
	private final TextField townField = FormFieldFactory.createFormTextField("Település", 200, true);
	private final TextField streetField = FormFieldFactory.createFormTextField("Utca", 200, true);
	private final TextField houseNumberField = FormFieldFactory.createFormTextField("Szám", 60, true);
	
	public AddressForm(String caption, Address address, ZipTownMap zipTownMap) {
		this.zipTownMap = zipTownMap;
		
		bindData(address);
		
		initZipField();
		initTownField();
	}
	
	private void bindData(Address address) {
		if(address != null) {
			zipField.setValue(address.zip);
			townField.setValue(address.town);
			streetField.setValue(address.street);
			houseNumberField.setValue(address.houseNumber);	
		}
	}
	
	private void initZipField() {
		//zipField.addValidator(new RegexpValidator("\\d{4}", "Hibás irányítószám"));
		zipField.addValueChangeListener(e -> townField.setValue(zipTownMap.getTown(e.getValue().toString())));
	}
	
	private void initTownField() {
		//townField.addValidator(new HungarianNameValidator("Hibás településnév"));
	}

	boolean isDataModified() {
		//return dataFields.stream().anyMatch(Field::isModified);
		return true;
	}
	
	boolean isValid() {
		//return dataFields.stream().allMatch(Field::isValid);
		return true;
	}
	
	@Override
	public Address getValue() {
		return new Address(zipField.getValue(), townField.getValue(), streetField.getValue(), houseNumberField.getValue());
	}

	@Override
	protected Component initContent() {
		VerticalLayout layout = new VerticalLayout();
		HorizontalLayout row1 = new HorizontalLayout(zipField, townField);
		HorizontalLayout row2 = new HorizontalLayout(streetField, houseNumberField);
		layout.addComponents(row1, row2);
		row1.setSizeFull();
		row2.setSizeFull();
		townField.setSizeFull();
		streetField.setSizeFull();
		layout.setSizeFull();
		return layout;
	}

	@Override
	protected void doSetValue(Address value) {
		throw new UnsupportedOperationException();
	}
	
}
