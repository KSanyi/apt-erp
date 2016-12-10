package apt.erp.common.domain;

public class Address {

	public static final Address empty = new Address("", "", "", "", "");
	
	public final String zip;
	public final String town;
	public final String street;
	public final String houseNumber;
	public final String comment;
	
	public Address(String zip, String town, String street, String number, String comment) {
		this.zip = zip;
		this.town = town;
		this.street = street;
		this.houseNumber = number;
		this.comment = comment;
	}
	
	public Address(String zip, String town, String street, String number) {
		this(zip, town, street, number, "");
	}

	public boolean matches(String filter) {
		String cleanedFilter = cleanString(filter); 
		return zip.contains(filter) || cleanString(town).contains(cleanedFilter) || cleanString(street).contains(cleanedFilter) || cleanString(comment).contains(cleanedFilter);
	}
	
	private String cleanString(String value) {
		return value.toLowerCase()
				.replace("ö", "o").replace("ő", "o").replace("ó", "o")
				.replace("ü", "u").replace("ű", "u").replace("ú", "u")
				.replace("á", "a").replace("í", "i").replace("é", "e");
	}
	
	@Override
	public String toString() {
		return zip + " " + town + " " + street + " " + houseNumber;
	}
	
}
