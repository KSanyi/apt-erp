package apt.erp.translatorservice.domain;

import java.util.Collections;
import java.util.List;

import apt.erp.projectservice.domain.LanguageService;

public class LanguageSkills {

	public static final LanguageSkills empty = new LanguageSkills(Collections.emptyList());
	
	private final List<LanguageService> services;

	public LanguageSkills(List<LanguageService> services) {
		this.services = services;
	}
	
	public List<LanguageService> services() {
		return services;
	}
	
}
