package apt.erp.customerservice.domain;

import static apt.erp.common.basic.StringUtil.containsIgnoreCase;

import apt.erp.common.SimpleValueObject;

public class CustomerId extends SimpleValueObject {

    public static CustomerId newId = new CustomerId("New customer");
    
	public CustomerId(String value) {
		super(value);
	}
	
	public boolean matches(String filter) {
		return containsIgnoreCase(value, filter);
	}

	@Override
	public boolean equals(Object other) {
		return super.equals(other) && getClass() == other.getClass();
	}
	
}
