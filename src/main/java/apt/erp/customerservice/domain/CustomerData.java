package apt.erp.customerservice.domain;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import apt.erp.projectservice.domain.Language;

public class CustomerData {

	public static CustomerData createEmpty() {
		return createNew(Name.createEmptyName(), Address.createEmptyAddress(), Optional.empty(), "",
		        Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
	}
	
	public static CustomerData createNew(Name name, Address address, Optional<Address> invoiceAddress, String comment,
	        List<Contact> contacts, List<Domain> domains, List<Language> languages) {
        return new CustomerData(CustomerId.newId, name, address, invoiceAddress, comment, contacts, domains, languages);
    }
	
	public final CustomerId customerId;
	public final Name name;
	public final Address address;
	public final Optional<Address> invoiceAddress;
	public final String comment;
	private final List<Domain> domains;
	private final List<Contact> contacts;
	private final List<Language> languages;
    
	public CustomerData(CustomerId customerId, Name name, Address address, Optional<Address> invoiceAddress, 
	        String comment, List<Contact> contacts, List<Domain> domains, List<Language> languages) {
		this.customerId = customerId;
	    this.name = name;
		this.address = address;
		this.invoiceAddress = invoiceAddress;
		this.comment = comment;
		this.domains = domains;
		this.contacts = contacts;
		this.languages = languages;
	}

	public Boolean invoiceAddressIsTheSame() {
        return !invoiceAddress.isPresent();
    }
	
	public boolean matches(String filter) {
		return customerId.matches(filter) ||
		       name.matches(filter) ||
		       domains.stream().anyMatch(domain -> domain.matches(filter)) ||
			   address != null && address.matches(filter) ||
			   invoiceAddress.map(address -> address.matches(filter)).orElse(false) ||
			   comment != null && comment.contains(filter);
	}
	
	public List<Domain> domains() {
	    return domains;
	}
	
	public List<Contact> contacts() {
        return contacts;
    }
	
	public List<Language> languages() {
        return languages;
    }
	
	@Override
	public String toString(){
		return name + " " + address;
	}
	
	public String toDetailedString(){
		return new StringBuilder()
		.append("Id: ").append(customerId).append(" ")
		.append("Name: ").append(name).append(", ")
		.append("domains: ").append(domains).append(", ")
		.append("languages: ").append(languages).append(", ")
		.append("address: ").append(address).append(", ")
		.append("invoice address: ").append(invoiceAddress.map(Address::toString).orElse("same")).append(", ")
		.append("contacts: ").append(contacts).append(", ")
		.append("comment: ").append(comment).append(" ").toString();
	}

}
