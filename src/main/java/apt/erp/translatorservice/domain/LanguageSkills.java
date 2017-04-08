package apt.erp.translatorservice.domain;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import apt.erp.projectservice.domain.Language;
import apt.erp.projectservice.domain.LanguageService;
import apt.erp.projectservice.domain.SubTopic;

public class LanguageSkills {

	public static final LanguageSkills empty = new LanguageSkills(Collections.emptyList(), Collections.emptyList(), Optional.empty(), Optional.empty());
	
	private final List<LanguageService> services;
	
	private final List<SubTopic> subTopics;
	
	public final Optional<String> interpeterId;
	
	public final Optional<String> translatorId;

	public LanguageSkills(List<LanguageService> services, List<SubTopic> subTopics, Optional<String> interpeterId, Optional<String> translatorId) {
		this.services = services;
		this.subTopics = subTopics;
		this.interpeterId = interpeterId;
		this.translatorId = translatorId;
	}
	
	public List<LanguageService> services() {
		return services;
	}
	
	public List<SubTopic> subTopics() {
		return subTopics;
	}
	
	public List<Language> languages() {
		return services.stream()
			.flatMap(service -> Stream.of(service.sourceLanguage, service.destLanguage))
			.distinct()
			.collect(Collectors.toList());
	}
	
	@Override
	public String toString() {
		return services.stream().map(LanguageService::toString).collect(Collectors.joining("\n")) +  "\nTopics: " + subTopics;
	}
	
}
