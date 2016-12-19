package apt.erp.projectservice.domain;

import apt.erp.common.domain.SimpleValueObject;

public class SubTopic extends SimpleValueObject {

	public final String name;

	public SubTopic(String name) {
		super(name);
		this.name = name;
	}
	
}
