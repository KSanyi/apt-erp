package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.projectservice.domain.SubTopic;
import apt.erp.projectservice.domain.Topic;

@SuppressWarnings("serial")
class TopicSelectorForm extends HorizontalLayout {

	private final ComboBox topicSelectorCombo = FormFieldFactory.createComboBox("", Topic.allTopics);
	private final ComboBox subTopicSelectorCombo = FormFieldFactory.createComboBox("", Collections.emptyList());
	
	private final List<Field<?>> dataFields = Arrays.asList(topicSelectorCombo, subTopicSelectorCombo);
	
	TopicSelectorForm(SubTopic subTopic) {
		bind(subTopic);
		
		topicSelectorCombo.addValueChangeListener(event -> topicChanged((Topic)event.getProperty().getValue()));
		
		createLayout();
	}
	
	private void topicChanged(Topic newTopic) {
		subTopicSelectorCombo.removeAllItems();
		subTopicSelectorCombo.addItems(newTopic.subTopics());
		subTopicSelectorCombo.setValue(newTopic.subTopics().get(0));
	}
	
	private void bind(SubTopic subTopic) {
		Topic topic = Topic.findForSubTopic(subTopic);
		topicSelectorCombo.setValue(topic);
		subTopicSelectorCombo.addItems(topic.subTopics());
		subTopicSelectorCombo.setValue(subTopic);
	}
	
	private void createLayout() {
		setSpacing(true);
		topicSelectorCombo.setWidth("120px");
		topicSelectorCombo.setStyleName(ValoTheme.COMBOBOX_TINY);
		subTopicSelectorCombo.setWidth("180px");
		subTopicSelectorCombo.setStyleName(ValoTheme.COMBOBOX_TINY);
		addComponents(topicSelectorCombo, subTopicSelectorCombo);
	}
	
	boolean isDataModified() {
		return dataFields.stream().anyMatch(Field::isModified);
	}
	
	SubTopic getSubTopic() {
		return (SubTopic)subTopicSelectorCombo.getValue();
	}
	
	
}
