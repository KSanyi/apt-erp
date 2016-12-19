package apt.erp.translatorservice.domain;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import apt.erp.projectservice.domain.LanguageService;
import apt.erp.projectservice.domain.SubTopic;

public class LanguageSkills {

	public static final LanguageSkills empty = new LanguageSkills(Collections.emptyList(), Collections.emptyList());
	
	private final List<LanguageService> services;
	
	private final List<SubTopic> subTopics; 

	public LanguageSkills(List<LanguageService> services, List<SubTopic> subTopics) {
		this.services = services;
		this.subTopics = subTopics;
	}
	
	public List<LanguageService> services() {
		return services;
	}
	
	public List<SubTopic> subTopics() {
		return subTopics;
	}
	
	@Override
	public String toString() {
		return services.stream().map(LanguageService::toString).collect(Collectors.joining("\n")) +  "\nTopics: " + subTopics;
	}
	
}
