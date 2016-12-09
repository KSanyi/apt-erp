package apt.erp.translatorservice.domain;

import java.util.Arrays;
import java.util.List;

public class PaymentInfo {

	public static PaymentInfo createEmpty() {
		return new PaymentInfo(SettlementMode.MonthEnd, 15, PaymentMode.Transfer);
	}
	
	public final SettlementMode settlementMode;
	public final int paymentDeadlineDays;
	public final PaymentMode paymentMode;
	
	public PaymentInfo(SettlementMode settlementMode, int paymentDeadlineDays, PaymentMode paymentMode) {
		this.settlementMode = settlementMode;
		this.paymentDeadlineDays = paymentDeadlineDays;
		this.paymentMode = paymentMode;
	}

	public static enum PaymentMode {

		Transfer("Átutalás"), PayPal("PayPal"), Cash("Kézpénz");
		
		private final String caption;
	    
		PaymentMode(String caption) {
	        this.caption = caption;
	    }
	    
	    public static List<PaymentMode> all = Arrays.asList(values());
	    
	    @Override
	    public String toString() {
	        return caption;
	    }
	}
	
	public static enum SettlementMode {
		
		MonthEnd("Hóvégi"), Custom("Egyedi");
		
		private final String caption;
	    
		SettlementMode(String caption) {
	        this.caption = caption;
	    }
	    
	    public static List<SettlementMode> all = Arrays.asList(values());
	    
	    @Override
	    public String toString() {
	        return caption;
	    }
	}
}
