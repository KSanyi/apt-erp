package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.projectservice.domain.SubTopic;
import apt.erp.projectservice.domain.Topic;

@SuppressWarnings("serial")
public class TopicsSelectorForm extends CustomField<List<SubTopic>> {

	private final Layout topicSelectorFormsLayout = new VerticalLayout();
	
	private final List<TopicSelectorForm> topicSelectorForms = new ArrayList<>();
    
    private final Button addTopicSelectorFormButton = new Button("Új téma");
    
    public TopicsSelectorForm(List<SubTopic> subTopics) {
    	bindData(subTopics);

		addTopicSelectorFormButton.addClickListener(click -> addTopicSelectorForm(Topic.allTopics.get(0).subTopics().get(0)));
	}

	private void bindData(List<SubTopic> subTopics) {
		subTopics.stream().forEach(this::addTopicSelectorForm);
	}
	
	private void addTopicSelectorForm(SubTopic subTopic) {
	    TopicSelectorForm topicSelectorForm = new TopicSelectorForm(subTopic);
	    Button removeButton = new Button(VaadinIcons.CLOSE);
	    removeButton.addStyleName(ValoTheme.BUTTON_TINY);
	    removeButton.addStyleName(ValoTheme.BUTTON_DANGER);
        
	    topicSelectorForms.add(topicSelectorForm);
	    HorizontalLayout layout = new HorizontalLayout(removeButton, topicSelectorForm);
	    layout.setSpacing(true);
	    layout.setComponentAlignment(removeButton, Alignment.BOTTOM_CENTER);
	    removeButton.addClickListener(click -> {
	    	topicSelectorFormsLayout.removeComponent(layout);
	    	topicSelectorForms.remove(topicSelectorForm);
	    });
			
	    topicSelectorFormsLayout.addComponent(layout);
	}
	
	@Override
	public List<SubTopic> getValue() {
		return topicSelectorForms.stream().map(TopicSelectorForm::getValue).collect(Collectors.toList());
	}

	@Override
	protected Component initContent() {
		addTopicSelectorFormButton.addStyleName(ValoTheme.BUTTON_TINY);
		addTopicSelectorFormButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    
	    return new Panel("Fordítási témák", LayoutFactory.createCenteredVerticalLayout(topicSelectorFormsLayout, addTopicSelectorFormButton));
	}

	@Override
	protected void doSetValue(List<SubTopic> value) {
		throw new UnsupportedOperationException();
	}
}
