package apt.erp.customerservice.ui.customerdatawindow.customerdataform;

import java.util.Optional;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.customerservice.domain.Address;

@SuppressWarnings("serial")
public class AddressTabSheet extends VerticalLayout {

	private final boolean invoiceAddressIsTheSame;
	
	private final AddressForm addressForm;
	private final AddressForm invoiceAddressForm;
	private final CheckBox invoiceAddressIsTheSameCheck = new CheckBox("Invoice address is the same");;
	
	private final TabSheet addressTabsheet = new TabSheet();
	
	public AddressTabSheet(Address address, Optional<Address> invoiceAddress, ZipTownMap zipTownMap) {

		invoiceAddressIsTheSame = !invoiceAddress.isPresent();
		addressForm = new AddressForm("Address", address, zipTownMap);
		invoiceAddressForm = new AddressForm("Invoice address", invoiceAddress.orElse(null), zipTownMap);
		
		addressTabsheet.addTab(addressForm, "Address", FontAwesome.ENVELOPE);
		addressTabsheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		if(!invoiceAddressIsTheSame) {
			addressTabsheet.addTab(invoiceAddressForm, "Invoice Address", FontAwesome.MONEY);
		}
		
		createLayout();
		
		invoiceAddressIsTheSameCheck.setValue(invoiceAddressIsTheSame);
		invoiceAddressIsTheSameCheck.addValueChangeListener(e -> {
			boolean same = (boolean)e.getProperty().getValue();
			if(same) {
				addressTabsheet.removeTab(addressTabsheet.getTab(1));
			} else {
				addressTabsheet.addTab(invoiceAddressForm, "Invoice Address", FontAwesome.MONEY);
				addressTabsheet.setSelectedTab(1);
			}
		});
	}
	
	private void createLayout() {
		setSpacing(true);
		addComponents(addressTabsheet, invoiceAddressIsTheSameCheck);
	}
	
	public Address getAddress() {
		return addressForm.getAddress();
	}
	
	public Optional<Address> getInvoiceAddress() {
		return invoiceAddressIsTheSameCheck.getValue() ? Optional.empty() : Optional.of(invoiceAddressForm.getAddress());
	}

	public boolean isDataModified() {
		return addressForm.isDataModified() || invoiceAddressIsTheSameCheck.getValue() != invoiceAddressIsTheSame || invoiceAddressForm.isDataModified();
	}

	public boolean isValid() {
		return addressForm.isValid() && (invoiceAddressIsTheSameCheck.getValue() || invoiceAddressForm.isValid());
	}
	
}
