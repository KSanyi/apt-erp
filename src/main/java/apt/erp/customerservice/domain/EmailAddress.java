package apt.erp.customerservice.domain;

import static apt.erp.common.basic.StringUtil.containsIgnoreCase;

import apt.erp.common.SimpleValueObject;

public class EmailAddress extends SimpleValueObject {

	static EmailAddress createEmptyEmailAddress() {
		return new EmailAddress("");
	}
	
	public EmailAddress(String value) {
		super(value);
	}
	
	public boolean matches(String filter) {
		return containsIgnoreCase(value, filter);
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof Name ? ((Name)other).value.equals(value) : false;
	}
}
