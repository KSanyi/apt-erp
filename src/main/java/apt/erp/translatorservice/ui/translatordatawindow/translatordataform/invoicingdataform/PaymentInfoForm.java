package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.invoicingdataform;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;

import apt.erp.common.basic.Util;
import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.translatorservice.domain.PaymentInfo;
import apt.erp.translatorservice.domain.PaymentInfo.PaymentMode;
import apt.erp.translatorservice.domain.PaymentInfo.SettlementMode;

@SuppressWarnings("serial")
public class PaymentInfoForm extends CustomField<PaymentInfo> {

    private final ComboBox<SettlementMode> settlementModeCombo = FormFieldFactory.createEnumComboBox("Elszámolás módja", SettlementMode.class);
    private final ComboBox<Integer> paymentDeadlineDaysCombo = FormFieldFactory.createComboBox("Fizetési határidő", Util.generateIntRangeList(1, 90));
    private final ComboBox<PaymentMode> paymentModeCombo = FormFieldFactory.createEnumComboBox("Fizetés módja", PaymentMode.class);

    public PaymentInfoForm(PaymentInfo paymentInfo) {
        setCaption("Számlázási módja");
        bindData(paymentInfo);
    }
    
    private void bindData(PaymentInfo paymentInfo) {
        settlementModeCombo.setValue(paymentInfo.settlementMode);
        paymentDeadlineDaysCombo.setValue(paymentInfo.paymentDeadlineDays);
        paymentModeCombo.setValue(paymentInfo.paymentMode);
    }
    
	@Override
	public PaymentInfo getValue() {
		return new PaymentInfo((SettlementMode)settlementModeCombo.getValue(), (Integer)paymentDeadlineDaysCombo.getValue(), (PaymentMode)paymentModeCombo.getValue());
	}

	@Override
	protected Component initContent() {
		settlementModeCombo.setWidth("100px");
        paymentDeadlineDaysCombo.setWidth("80px");
        paymentModeCombo.setWidth("100px");
        HorizontalLayout layout = LayoutFactory.createHorizontalLayout(settlementModeCombo, paymentDeadlineDaysCombo, paymentModeCombo);
        layout.setMargin(true);
        setSizeFull();
		return layout;
	}

	@Override
	protected void doSetValue(PaymentInfo value) {
		throw new UnsupportedOperationException();
	}
    
}
