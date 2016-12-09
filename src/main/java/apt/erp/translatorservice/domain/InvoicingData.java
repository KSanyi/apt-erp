package apt.erp.translatorservice.domain;

import java.time.LocalDate;
import java.util.Optional;

public class InvoicingData {

	public static InvoicingData createEmpty() {
		return new InvoicingData(Optional.empty(), PaymentInfo.createEmpty(), Optional.empty());
	}
	
	public final Optional<LocalDate> contractingDate;
	
	public final PaymentInfo paymentInfo;
	
	public final Optional<InvoicingCompany> invoicingCompany;
	
	public InvoicingData(Optional<LocalDate> contractingDate, PaymentInfo paymentInfo, Optional<InvoicingCompany> invoicingCompany) {
		this.contractingDate = contractingDate;
		this.paymentInfo = paymentInfo;
		this.invoicingCompany = invoicingCompany;
	}

}
