package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.invoicingdataform;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;

import apt.erp.common.domain.TaxId;
import apt.erp.common.vaadin.AddressTabSheet;
import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.translatorservice.domain.InvoicingCompany;
import apt.erp.translatorservice.domain.InvoicingCompany.InvoicingType;

@SuppressWarnings("serial")
public class InvoicingCompanyForm extends Panel {

    private final TextField nameField = FormFieldFactory.createFormTextField("Név", 300, true);
    private final TextField taxIdField = FormFieldFactory.createFormTextField("Adószám", 120, false);
    private final ComboBox invoicingTypeCombo = FormFieldFactory.createComboBox("Számlázási metódus", InvoicingType.all);
    private final AddressTabSheet addressTabSheet;
    private final CheckBox vatFreeCheck = new CheckBox("Áfa mentes");
    
    private final List<Field<?>> dataFields = Arrays.asList(nameField, taxIdField, invoicingTypeCombo, vatFreeCheck);
    
    public InvoicingCompanyForm(InvoicingCompany invoicingCompany, ZipTownMap zipTownMap) {
        setCaption("Számlázó cég");
        addressTabSheet = new AddressTabSheet(invoicingCompany.postalAddress, invoicingCompany.invoiceAddress, zipTownMap);
        bindData(invoicingCompany);
        createLayout();
    }
    
    private void bindData(InvoicingCompany invoicingCompany) {
        nameField.setPropertyDataSource(new ObjectProperty<>(invoicingCompany.name));
        taxIdField.setPropertyDataSource(new ObjectProperty<>(invoicingCompany.taxId.value));
        invoicingTypeCombo.setPropertyDataSource(new ObjectProperty<>(invoicingCompany.invoicingType));
        invoicingTypeCombo.setBuffered(true);
        vatFreeCheck.setPropertyDataSource(new ObjectProperty<>(invoicingCompany.vatFree));
        vatFreeCheck.setBuffered(true);
    }
    
    private void createLayout() {
        invoicingTypeCombo.setWidth("100");
        setContent(LayoutFactory.createVerticalLayout(nameField, 
                LayoutFactory.createHorizontalLayout(taxIdField, invoicingTypeCombo),
                vatFreeCheck,
                addressTabSheet));
        setSizeFull();        
        nameField.focus();
    }
    
    public boolean isDataModified() {
        return dataFields.stream().anyMatch(Field::isModified) || addressTabSheet.isDataModified();
    }
    
    public boolean isDataValid() {
        return dataFields.stream().allMatch(Field::isValid) && addressTabSheet.isValid();
    }

    public InvoicingCompany getInvoicingCompany() {
        return new InvoicingCompany(nameField.getValue(), new TaxId(taxIdField.getValue()), (InvoicingType)invoicingTypeCombo.getValue(),
                addressTabSheet.getAddress(), addressTabSheet.getInvoiceAddress(), vatFreeCheck.getValue());
    }
    
}
