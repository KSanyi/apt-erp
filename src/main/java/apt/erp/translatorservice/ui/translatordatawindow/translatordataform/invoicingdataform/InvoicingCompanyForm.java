package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.invoicingdataform;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import apt.erp.common.domain.TaxId;
import apt.erp.common.vaadin.AddressTabSheet;
import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.translatorservice.domain.InvoicingCompany;
import apt.erp.translatorservice.domain.InvoicingCompany.InvoicingType;

@SuppressWarnings("serial")
public class InvoicingCompanyForm extends CustomField<InvoicingCompany> {

    private final TextField nameField = FormFieldFactory.createFormTextField("Név", 300, true);
    private final TextField taxIdField = FormFieldFactory.createFormTextField("Adószám", 120, false);
    private final ComboBox<InvoicingType> invoicingTypeCombo = FormFieldFactory.createEnumComboBox("Számlázási metódus", InvoicingType.class);
    private final AddressTabSheet addressTabSheet;
    private final CheckBox vatFreeCheck = new CheckBox("Áfa mentes");
    
    public InvoicingCompanyForm(InvoicingCompany invoicingCompany, ZipTownMap zipTownMap) {
        setCaption("Számlázó cég");
        addressTabSheet = new AddressTabSheet(invoicingCompany.postalAddress, invoicingCompany.invoiceAddress, zipTownMap);
        bindData(invoicingCompany);
    }
    
    private void bindData(InvoicingCompany invoicingCompany) {
        nameField.setValue(invoicingCompany.name);
        taxIdField.setValue(invoicingCompany.taxId.value);
        invoicingTypeCombo.setValue(invoicingCompany.invoicingType);
        vatFreeCheck.setValue(invoicingCompany.vatFree);
    }
    
	@Override
	public InvoicingCompany getValue() {
		return new InvoicingCompany(nameField.getValue(), new TaxId(taxIdField.getValue()), invoicingTypeCombo.getValue(),
                addressTabSheet.getAddress(), addressTabSheet.getInvoiceAddress(), vatFreeCheck.getValue());
	}

	@Override
	protected void doSetValue(InvoicingCompany value) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Component initContent() {
		invoicingTypeCombo.setWidth("100");
        VerticalLayout layout = new VerticalLayout(nameField, 
                LayoutFactory.createHorizontalLayout(taxIdField, invoicingTypeCombo),
                vatFreeCheck,
                addressTabSheet);
        setSizeFull();        
        nameField.focus();
        return layout;
	}
    
}
