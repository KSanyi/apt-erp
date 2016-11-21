package apt.erp.customerservice.domain;

import java.util.Arrays;
import java.util.List;

public class CustomerData {

	static CustomerData createEmptyCustomerData() {
		return new CustomerData(Name.createEmptyName(), Address.createEmptyAddress(), true, null, "");
	}
	
	public final Name name;
	public final Address address;
	public final boolean invoiceAddressIsTheSame;
	public final Address invoiceAddress;
	public final String comment;
	private final List<Domain> domains;
	
	public CustomerData(Name name, Address address, boolean invoiceAddressIsTheSame, Address invoiceAddress, String comment, Domain ... domains) {
		this.name = name;
		this.address = address;
		this.invoiceAddressIsTheSame = invoiceAddressIsTheSame;
		this.invoiceAddress = invoiceAddress;
		this.comment = comment;
		this.domains = Arrays.asList(domains);
	}

	public boolean matches(String filter) {
		return name.matches(filter) ||
		       domains.stream().anyMatch(domain -> filter.matches(filter)) ||
			   address != null && address.matches(filter) ||
			   invoiceAddress != null && invoiceAddress.matches(filter) ||
			   comment != null && comment.contains(filter);
	}
	
	public List<Domain> domains() {
	    return domains;
	}
	
	@Override
	public String toString(){
		return name + " " + address;
	}
	
	public String toDetailedString(){
		return new StringBuilder()
		.append("Name: ").append(name).append(", ")
		.append("domains: ").append(domains).append(", ")
		.append("address: ").append(address).append(", ")
		.append("invoice address: ").append(invoiceAddressIsTheSame ? "same" : invoiceAddress).append(", ")
		.append("comment: ").append(comment).append(" ").toString();
	}

}
