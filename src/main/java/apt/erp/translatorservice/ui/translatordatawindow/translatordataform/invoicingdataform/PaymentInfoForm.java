package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.invoicingdataform;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;

import apt.erp.common.basic.Util;
import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.translatorservice.domain.PaymentInfo;
import apt.erp.translatorservice.domain.PaymentInfo.PaymentMode;
import apt.erp.translatorservice.domain.PaymentInfo.SettlementMode;

@SuppressWarnings("serial")
public class PaymentInfoForm extends Panel {

    private final ComboBox settlementModeCombo = FormFieldFactory.createComboBox("Elszámolás módja", SettlementMode.all);
    private final ComboBox paymentDeadlineDaysCombo = FormFieldFactory.createComboBox("Fizetési határidő", Util.generateIntRangeList(1, 90));
    private final ComboBox paymentModeCombo = FormFieldFactory.createComboBox("Fizetés módja", PaymentMode.all);

    private final List<Field<?>> dataFields = Arrays.asList(settlementModeCombo, paymentDeadlineDaysCombo, paymentModeCombo);
    
    public PaymentInfoForm(PaymentInfo paymentInfo) {
        setCaption("Számlázási módja");
        bindData(paymentInfo);
        
        createLayout();
    }
    
    private void bindData(PaymentInfo paymentInfo) {
        settlementModeCombo.setPropertyDataSource(new ObjectProperty<>(paymentInfo.settlementMode));
        settlementModeCombo.setBuffered(true);
        
        paymentDeadlineDaysCombo.setBuffered(true);
        
        paymentDeadlineDaysCombo.setPropertyDataSource(new ObjectProperty<>(paymentInfo.paymentDeadlineDays));
        paymentModeCombo.setPropertyDataSource(new ObjectProperty<>(paymentInfo.paymentMode));
    }
    
    private void createLayout() {
        settlementModeCombo.setWidth("100px");
        paymentDeadlineDaysCombo.setWidth("80px");
        paymentModeCombo.setWidth("100px");
        HorizontalLayout layout = LayoutFactory.createHorizontalLayout(settlementModeCombo, paymentDeadlineDaysCombo, paymentModeCombo);
        layout.setMargin(true);
        setContent(layout);
        setSizeFull();
    }
    
    public boolean isDataModified() {
        return dataFields.stream().anyMatch(Field::isModified);
    }

    public boolean isDataValid() {
        return true;
    }
    
    public PaymentInfo getPaymentInfo() {
        return new PaymentInfo((SettlementMode)settlementModeCombo.getValue(), (Integer)paymentDeadlineDaysCombo.getValue(), (PaymentMode)paymentModeCombo.getValue());
    }
    
}
