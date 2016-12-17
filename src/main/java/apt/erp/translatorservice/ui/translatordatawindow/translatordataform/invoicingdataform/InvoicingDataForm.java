package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.invoicingdataform;

import java.util.Date;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.basic.DateUtil;
import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.translatorservice.domain.InvoicingCompany;
import apt.erp.translatorservice.domain.InvoicingData;

@SuppressWarnings("serial")
public class InvoicingDataForm extends VerticalLayout {

    public final InvoicingData invoicingData;
    
    private final DateField contarctingDateField = new DateField("Szerződéskötés dátuma");
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
        
        createLayout();
    }
    
    private void bindData(InvoicingData invoicingData) {
        contarctingDateField.setPropertyDataSource(new ObjectProperty<Date>(invoicingData.contractingDate.map(DateUtil::convertToDate).orElse(null), Date.class));
        
        hasInvoicingCompanyCheck.setPropertyDataSource(new ObjectProperty<>(invoicingData.invoicingCompany.isPresent()));
        hasInvoicingCompanyCheck.setBuffered(true);
        hasInvoicingCompanyCheck.addValueChangeListener(value -> invoicingCompanyForm.setVisible((Boolean)value.getProperty().getValue()));
    }
    
    public boolean isDataValid() {
        return paymentInfoForm.isDataValid();
    }

    public boolean isDataModified() {
        return paymentInfoForm.isDataModified();
    }
    
    private void createLayout() {
        
        setMargin(true);
        setSpacing(true);
        
        contarctingDateField.addStyleName(ValoTheme.DATEFIELD_SMALL);
        addComponents(contarctingDateField, paymentInfoForm, hasInvoicingCompanyCheck, invoicingCompanyForm);
    }
}
