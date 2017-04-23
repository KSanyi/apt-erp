package apt.erp.common.vaadin;

import java.util.Optional;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.domain.Address;

@SuppressWarnings("serial")
public class AddressTabSheet extends Panel {

	private final boolean invoiceAddressIsTheSame;
	
	private final AddressForm addressForm;
	private final AddressForm invoiceAddressForm;
	private final CheckBox invoiceAddressIsTheSameCheck = new CheckBox("Számlázási cím megegyezik");
	
	private final TabSheet tabSheet = new TabSheet();
	
	public AddressTabSheet(Address address, Optional<Address> invoiceAddress, ZipTownMap zipTownMap) {

		invoiceAddressIsTheSame = !invoiceAddress.isPresent();
		addressForm = new AddressForm("Address", address, zipTownMap);
		invoiceAddressForm = new AddressForm("Invoice address", invoiceAddress.orElse(null), zipTownMap);
		
		tabSheet.addTab(addressForm, "Cím", VaadinIcons.ENVELOPE);
		if(!invoiceAddressIsTheSame) {
			tabSheet.addTab(invoiceAddressForm, "Számlázási cím", VaadinIcons.MONEY);
		}
		
		createLayout();
		
		invoiceAddressIsTheSameCheck.setValue(invoiceAddressIsTheSame);
		invoiceAddressIsTheSameCheck.addValueChangeListener(e -> {
			boolean same = e.getValue();
			if(same) {
				tabSheet.removeTab(tabSheet.getTab(1));
			} else {
				tabSheet.addTab(invoiceAddressForm, "Számlázási cím", VaadinIcons.MONEY);
				tabSheet.setSelectedTab(1);
			}
		});
	}
	
	private void createLayout() {
		tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabSheet.addStyleName(ValoTheme.TABSHEET_COMPACT_TABBAR);
		setContent(new VerticalLayout(tabSheet, invoiceAddressIsTheSameCheck));
		setSizeFull();
	}
	
	public Address getAddress() {
		return addressForm.getValue();
	}
	
	public Optional<Address> getInvoiceAddress() {
		return invoiceAddressIsTheSameCheck.getValue() ? Optional.empty() : Optional.of(invoiceAddressForm.getValue());
	}
	
}
