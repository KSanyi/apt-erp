package apt.erp.customerservice.domain;

public class CustomerData {

	static CustomerData createEmptyCustomerData() {
		return new CustomerData(Name.createEmptyName(), Address.createEmptyAddress(), true, null, EmailAddress.createEmptyEmailAddress(), PhoneNumber.createEmptyPhoneNumber(), "");
	}
	
	public final Name name;
	public final Address address;
	public final boolean invoiceAddressIsTheSame;
	public final Address invoiceAddress;
	public final EmailAddress emailAddress;
	public final PhoneNumber phoneNumber;
	public final String comment;
	
	public CustomerData(Name name, Address address, boolean invoiceAddressIsTheSame, Address invoiceAddress, EmailAddress emailAddress,
			PhoneNumber phoneNumber, String comment) {
		this.name = name;
		this.address = address;
		this.invoiceAddressIsTheSame = invoiceAddressIsTheSame;
		this.invoiceAddress = invoiceAddress;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.comment = comment;
	}

	public boolean matches(String filter) {
		return name.matches(filter) ||
			   address != null && address.matches(filter) ||
			   invoiceAddress != null && invoiceAddress.matches(filter) ||
			   emailAddress != null && emailAddress.matches(filter) ||
			   phoneNumber != null && phoneNumber.matches(filter) ||
			   comment != null && comment.contains(filter);
	}
	
	@Override
	public String toString(){
		return name + " " + address;
	}
	
	public String toDetailedString(){
		return new StringBuilder()
		.append("Name: ").append(name).append(" ")
		.append("address: ").append(address).append(" ")
		.append("invoice address: ").append(invoiceAddressIsTheSame ? "same" : invoiceAddress).append(" ")
		.append("email: ").append(emailAddress).append(" ")
		.append("phone: ").append(phoneNumber).append(" ")
		.append("comment: ").append(comment).append(" ").toString();
	}

}
