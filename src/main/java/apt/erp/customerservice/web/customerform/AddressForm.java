package apt.erp.customerservice.web.customerform;

import java.util.Arrays;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.customerservice.domain.Address;

@SuppressWarnings("serial")
public class AddressForm extends Panel {

	private final ZipTownMap zipTownMap;
	
	private final TextField zipField = FormFieldFactory.createFormTextField("Zip", 50, true);
	private final TextField townField = FormFieldFactory.createFormTextField("Town", 150, true);
	private final TextField streetField = FormFieldFactory.createFormTextField("Street", 150, true);
	private final TextField houseNumberField = FormFieldFactory.createFormTextField("Number", 50, true);
	
	public AddressForm(String caption, Address address, ZipTownMap zipTownMap) {
		setCaption(caption);
		setStyleName(Reindeer.PANEL_LIGHT);
		this.zipTownMap = zipTownMap;
		
		bindData(address);
		
		initZipField();
		initTownField();
		
		createLayout();
	}
	
	private void createLayout() {
		HorizontalLayout firstRow = new HorizontalLayout(zipField, townField);
		firstRow.setSpacing(true);
		
		HorizontalLayout secondRow = new HorizontalLayout(streetField, houseNumberField);
		secondRow.setSpacing(true);
		
		VerticalLayout layout = new VerticalLayout(firstRow, secondRow);
		layout.setSpacing(true);
		setContent(layout);
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
		zipField.addValidator(new RegexpValidator("\\d{4}", "Invalid zip code"));
		zipField.addValueChangeListener(e -> townField.setValue(zipTownMap.getTown(e.getProperty().getValue().toString())));
	}
	
	private void initTownField() {
		townField.addValidator(new HungarianNameValidator("Invalid town name"));
	}

	boolean isDataModified() {
		return Arrays.asList(zipField, townField, streetField, houseNumberField).stream().anyMatch(f -> f.isModified());
	}
	
	boolean isValid() {
		return Arrays.asList(zipField, townField, streetField, houseNumberField).stream().allMatch(f -> f.isValid());
	}
	
	Address getChangedAddress() {
		return new Address(zipField.getValue(), townField.getValue(), streetField.getValue(), houseNumberField.getValue());
	}
	
}
