package apt.erp.customerservice.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import apt.erp.common.domain.Address;
import apt.erp.common.domain.Name;
import apt.erp.common.domain.TaxId;
import apt.erp.projectservice.domain.Language;

public class CustomerData {

	public static CustomerData createEmpty() {
		return createNew(TaxId.Unknown, Name.createEmpty(), Address.createEmptyAddress(), Optional.empty(), "",
		        Domain.Other, Language.English, Collections.emptyList());
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
		String[] filterParts = filter.split(" ");
		return Stream.of(filterParts).allMatch(filterPart -> 
			   customerId.matches(filterPart) ||
			   taxId.matches(filterPart) ||
		       name.matches(filterPart) ||
			   address != null && address.matches(filterPart) ||
			   invoiceAddress.map(address -> address.matches(filterPart)).orElse(false) ||
			   comment != null && comment.contains(filterPart));
	}
	
	public List<Contact> contacts() {
        return new ArrayList<>(contacts);
    }
	
	@Override
	public boolean equals(Object other){
		if(this == other)
			return true;
		
		if(other == null)
			return false;
		
		if(this.getClass() != other.getClass())
			return false;

		return this.customerId.equals(((CustomerData)other).customerId);
	}
	
	@Override
	public int hashCode() {
		return customerId.hashCode();
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
