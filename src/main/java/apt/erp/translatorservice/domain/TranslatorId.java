package apt.erp.translatorservice.domain;

import static apt.erp.common.basic.StringUtil.containsIgnoreCase;

import apt.erp.common.domain.SimpleValueObject;

public class TranslatorId extends SimpleValueObject {

    public static TranslatorId newId = new TranslatorId("New translator");
    
	public TranslatorId(String value) {
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
