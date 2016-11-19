package apt.erp.customerservice.domain;

import apt.erp.common.SimpleValueObject;

public class Name extends SimpleValueObject {

	static Name createEmptyName() {
		return new Name("");
	}
	
	public Name(String value) {
		super(value);
	}
	
	public boolean matches(String filter) {
		return cleanName().contains(cleanString(filter));
	}
	
	private String cleanString(String value) {
		return value.toLowerCase()
				.replace("ő", "o").replace("ö", "o").replace("ó", "o")
				.replace("ű", "u").replace("ü", "u").replace("ú", "u")
				.replace("á", "a").replace("í", "i").replace("é", "e");
	}
	
	public String cleanName() {
		return cleanString(value);
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof Name ? ((Name)other).value.equals(value) : false;
	}
	
}
