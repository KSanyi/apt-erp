package apt.erp.translatorservice.domain;

import java.util.Optional;

import apt.erp.common.domain.Address;
import apt.erp.common.domain.TaxId;

public class InvoicingCompany {

	public final String name;
	
	public final TaxId taxId;
	
	public final InvoicingType invoicingType;
	
	public final Address postalAddress;
	
	public final Optional<Address> invoiceAddress;
	
	public boolean vatFree;
	
	public InvoicingCompany(String name, TaxId taxId, InvoicingType invoicingType, Address postalAddress,
			Optional<Address> invoiceAddress, boolean vatFree) {
		this.name = name;
		this.taxId = taxId;
		this.invoicingType = invoicingType;
		this.postalAddress = postalAddress;
		this.invoiceAddress = invoiceAddress;
		this.vatFree = vatFree;
	}

	public static enum InvoicingType {
		Normal, KATA, EVA
	}
	
}
