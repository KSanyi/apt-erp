package apt.erp.projectservice.domain;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import apt.erp.infrastructure.ResourceFileLoader;

public class Topic {

	public final String name;
	
	private final List<SubTopic> subTopics;

	private Topic(String name, List<SubTopic> subTopics) {
		this.name = name;
		this.subTopics = subTopics;
	}
	
	public List<SubTopic> subTopics() {
		return subTopics;
	}
	
	public static final List<Topic> allTopics = loadTopics();
	
	private static List<Topic> loadTopics() {
		try {
			List<String> lines = Files.readAllLines(ResourceFileLoader.loadPath("Topics.yml"));
			List<Topic> topics = new ArrayList<>();
			String actualTopicName = null;
			List<SubTopic> actualSubTopics = new ArrayList<>();
			for(String line : lines) {
				if(!line.startsWith("  ")) {
					if(actualTopicName != null) {
						topics.add(new Topic(actualTopicName, actualSubTopics));
						actualSubTopics = new ArrayList<>();
					}
					actualTopicName = line;
				} else {
					actualSubTopics.add(new SubTopic(line.trim()));					
				}
			}
			
			return topics;
		} catch (Exception ex) {
			throw new RuntimeException("Could not load topics file from classpath", ex);
		}
	}
	
	public static Topic findForSubTopic(SubTopic subTopic) {
		return allTopics.stream().filter(topic -> topic.subTopics.contains(subTopic)).findFirst().get();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
