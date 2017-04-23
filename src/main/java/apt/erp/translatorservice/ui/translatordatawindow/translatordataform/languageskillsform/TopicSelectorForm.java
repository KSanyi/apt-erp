package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import java.util.Collections;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.projectservice.domain.SubTopic;
import apt.erp.projectservice.domain.Topic;

@SuppressWarnings("serial")
class TopicSelectorForm extends CustomField<SubTopic> {

	private final ComboBox<Topic> topicSelectorCombo = FormFieldFactory.createComboBox("", Topic.allTopics);
	private final ComboBox<SubTopic> subTopicSelectorCombo = FormFieldFactory.createComboBox("", Collections.emptyList());
	
	TopicSelectorForm(SubTopic subTopic) {
		bind(subTopic);
		
		topicSelectorCombo.addValueChangeListener(e -> topicChanged(e.getValue()));
	}
	
	private void topicChanged(Topic newTopic) {
		subTopicSelectorCombo.setItems(newTopic.subTopics());
		subTopicSelectorCombo.setValue(newTopic.subTopics().get(0));
	}
	
	private void bind(SubTopic subTopic) {
		Topic topic = Topic.findForSubTopic(subTopic);
		topicSelectorCombo.setValue(topic);
		subTopicSelectorCombo.setItems(topic.subTopics());
		subTopicSelectorCombo.setValue(subTopic);
	}
	
	@Override
	public SubTopic getValue() {
		return subTopicSelectorCombo.getValue();
	}

	@Override
	protected Component initContent() {
		topicSelectorCombo.setWidth("120px");
		topicSelectorCombo.setStyleName(ValoTheme.COMBOBOX_TINY);
		subTopicSelectorCombo.setWidth("180px");
		subTopicSelectorCombo.setStyleName(ValoTheme.COMBOBOX_TINY);
		return new HorizontalLayout(topicSelectorCombo, subTopicSelectorCombo);
	}

	@Override
	protected void doSetValue(SubTopic value) {
		throw new UnsupportedOperationException();
	}
	
}
