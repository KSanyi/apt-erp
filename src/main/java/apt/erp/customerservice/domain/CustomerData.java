package apt.erp.customerservice.domain;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import apt.erp.projectservice.domain.Language;

public class CustomerData {

	public static CustomerData createEmpty() {
		return createNew(TaxId.Unknown, Name.createEmptyName(), Address.createEmptyAddress(), Optional.empty(), "",
		        Domain.Other, Language.Other, Collections.emptyList());
	}
	
	public static CustomerData createNew(TaxId taxId, Name name, Address address, Optional<Address> invoiceAddress, String comment,
	        Domain mainDomain, Language mainLanguage, List<Contact> contacts) {
        return new CustomerData(CustomerId.newId, taxId, name, address, invoiceAddress, comment, mainDomain, mainLanguage, contacts);
    }
	
	public final CustomerId customerId;
	public final Name name;
	public final TaxId taxId;
	public final Address address;
	public final Optional<Address> invoiceAddress;
	public final String comment;
	public final Domain mainDomain;
	public final Language mainLanguage;
	private final List<Contact> contacts;
    
	public CustomerData(CustomerId customerId, TaxId taxId, Name name, Address address, Optional<Address> invoiceAddress, 
	        String comment, Domain mainDomain, Language mainLanguage, List<Contact> contacts) {
		this.customerId = customerId;
		this.taxId = taxId;
	    this.name = name;
		this.address = address;
		this.invoiceAddress = invoiceAddress;
		this.comment = comment;
		this.mainDomain = mainDomain;
		this.contacts = contacts;
		this.mainLanguage = mainLanguage;
	}
	
	public CustomerData updated(TaxId taxId, Name name, Address address, Optional<Address> invoiceAddress, 
            String comment, Domain mainDomain, Language mainLanguage, List<Contact> contacts) {
	    return new CustomerData(customerId, taxId, name, address, invoiceAddress, comment, mainDomain, mainLanguage, contacts);
	}

	public Boolean invoiceAddressIsTheSame() {
        return !invoiceAddress.isPresent();
    }
	
	public boolean matches(String filter) {
		return customerId.matches(filter) ||
		       name.matches(filter) ||
			   address != null && address.matches(filter) ||
			   invoiceAddress.map(address -> address.matches(filter)).orElse(false) ||
			   comment != null && comment.contains(filter);
	}
	
	public List<Contact> contacts() {
        return contacts;
    }
	
	@Override
	public String toString(){
		return name + " " + address;
	}
	
	public String toDetailedString(){
		return new StringBuilder()
		.append("Id: ").append(customerId).append(" ")
		.append("Name: ").append(name).append(", ")
		.append("Main domain: ").append(mainDomain).append(", ")
		.append("Main language: ").append(mainLanguage).append(", ")
		.append("address: ").append(address).append(", ")
		.append("invoice address: ").append(invoiceAddress.map(Address::toString).orElse("same")).append(", ")
		.append("contacts: ").append(contacts).append(", ")
		.append("comment: ").append(comment).append(" ").toString();
	}

}
