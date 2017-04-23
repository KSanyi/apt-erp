package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.invoicingdataform;

import java.time.LocalDate;
import java.util.Optional;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.DateField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.translatorservice.domain.InvoicingCompany;
import apt.erp.translatorservice.domain.InvoicingData;

@SuppressWarnings("serial")
public class InvoicingDataForm extends CustomField<InvoicingData> {

    public final InvoicingData invoicingData;
    
    private final DateField contractingDateField = new DateField("Szerződéskötés dátuma");
    private final PaymentInfoForm paymentInfoForm;
    private final CheckBox hasInvoicingCompanyCheck = new CheckBox("Van számlázási cég");
    private final InvoicingCompanyForm invoicingCompanyForm;
    
    public InvoicingDataForm(InvoicingData invoicingData, ZipTownMap zipTownMap) {
        this.invoicingData = invoicingData;
        
        paymentInfoForm = new PaymentInfoForm(invoicingData.paymentInfo);
        
        bindData(invoicingData);
        
        if(invoicingData.invoicingCompany.isPresent()) {
            invoicingCompanyForm = new InvoicingCompanyForm(invoicingData.invoicingCompany.get(), zipTownMap);
        } else {
            invoicingCompanyForm = new InvoicingCompanyForm(InvoicingCompany.empty, zipTownMap);
            invoicingCompanyForm.setVisible(false);
        }
        
    }
    
    private void bindData(InvoicingData invoicingData) {

    	contractingDateField.setValue(invoicingData.contractingDate.orElse(null));
        
        hasInvoicingCompanyCheck.setValue(invoicingData.invoicingCompany.isPresent());
        hasInvoicingCompanyCheck.addValueChangeListener(value -> invoicingCompanyForm.setVisible(value.getValue()));
    }
    
	@Override
	public InvoicingData getValue() {
		Optional<LocalDate> contractingDate = Optional.ofNullable(contractingDateField.getValue());
    	Optional<InvoicingCompany> invoicingCompany = hasInvoicingCompanyCheck.getValue() ? Optional.of(invoicingCompanyForm.getValue()) : Optional.empty();
    	
    	return new InvoicingData(contractingDate, paymentInfoForm.getValue(), invoicingCompany);
	}

	@Override
	protected Component initContent() {
		contractingDateField.addStyleName(ValoTheme.DATEFIELD_SMALL);
		return new VerticalLayout(contractingDateField, paymentInfoForm, hasInvoicingCompanyCheck, invoicingCompanyForm);
	}

	@Override
	protected void doSetValue(InvoicingData value) {
		throw new UnsupportedOperationException();
	}
    
}
