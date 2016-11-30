package apt.erp.common.domain;

import static apt.erp.common.basic.StringUtil.containsIgnoreCase;

public class EmailAddress extends SimpleValueObject {

    public static EmailAddress createEmptyEmailAddress() {
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
