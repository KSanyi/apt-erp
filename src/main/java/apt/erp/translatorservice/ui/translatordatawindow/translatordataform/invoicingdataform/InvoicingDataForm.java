package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.invoicingdataform;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.basic.DateUtil;
import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.translatorservice.domain.InvoicingCompany;
import apt.erp.translatorservice.domain.InvoicingData;

@SuppressWarnings("serial")
public class InvoicingDataForm extends VerticalLayout {

    public final InvoicingData invoicingData;
    
    private final DateField contractingDateField = new DateField("Szerződéskötés dátuma");
    private final PaymentInfoForm paymentInfoForm;
    private final CheckBox hasInvoicingCompanyCheck = new CheckBox("Van számlázási cég");
    private final InvoicingCompanyForm invoicingCompanyForm;
    
    private final List<Field<?>> dataFields = Arrays.asList(contractingDateField, hasInvoicingCompanyCheck);
    
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
        
        createLayout();
    }
    
    private void bindData(InvoicingData invoicingData) {

    	contractingDateField.setPropertyDataSource(new ObjectProperty<Date>(invoicingData.contractingDate.map(DateUtil::convertToDate).orElse(null), Date.class));
        
        hasInvoicingCompanyCheck.setPropertyDataSource(new ObjectProperty<>(invoicingData.invoicingCompany.isPresent()));
        hasInvoicingCompanyCheck.setBuffered(true);
        hasInvoicingCompanyCheck.addValueChangeListener(value -> invoicingCompanyForm.setVisible((Boolean)value.getProperty().getValue()));
    }
    
    private void createLayout() {
        setMargin(true);
        setSpacing(true);
        
        contractingDateField.addStyleName(ValoTheme.DATEFIELD_SMALL);
        addComponents(contractingDateField, paymentInfoForm, hasInvoicingCompanyCheck, invoicingCompanyForm);
    }
    
    public boolean isDataValid() {
        return dataFields.stream().allMatch(Field::isValid) && paymentInfoForm.isDataValid() && (!hasInvoicingCompanyCheck.getValue() || invoicingCompanyForm.isDataValid());
    }

    public boolean isDataModified() {
        return dataFields.stream().anyMatch(Field::isModified) || paymentInfoForm.isDataModified() || (hasInvoicingCompanyCheck.getValue() && invoicingCompanyForm.isDataModified());
    }
    
    public InvoicingData getInvoicingData() {
    	Optional<LocalDate> contractingDate = Optional.ofNullable(contractingDateField.getValue()).map(DateUtil::convertToLocalDate);
    	Optional<InvoicingCompany> invoicingCompany = hasInvoicingCompanyCheck.getValue() ? Optional.of(invoicingCompanyForm.getInvoicingCompany()) : Optional.empty();
    	
    	return new InvoicingData(contractingDate, paymentInfoForm.getPaymentInfo(), invoicingCompany);
    }
    
}
