package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.projectservice.domain.SubTopic;
import apt.erp.projectservice.domain.Topic;

@SuppressWarnings("serial")
public class TopicsSelectorForm extends Panel {

	private final Layout topicSelectorFormsLayout = new VerticalLayout();
	
	private final List<TopicSelectorForm> topicSelectorForms = new ArrayList<>();
    
    private final Button addTopicSelectorFormButton = new Button("Új téma");
    
    private boolean addOrRemoveButtonWasCicked = false;
    
    public TopicsSelectorForm(List<SubTopic> subTopics) {
    	bindData(subTopics);
		createValidators();

		addTopicSelectorFormButton.addClickListener(click -> addTopicSelectorForm(Topic.allTopics.get(0).subTopics().get(0)));
		
		createLayout();
	}

	private void createLayout() {
		setCaption("Fordítási témák");
		addTopicSelectorFormButton.addStyleName(ValoTheme.BUTTON_TINY);
		addTopicSelectorFormButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    
	    setContent(LayoutFactory.createCenteredVerticalLayout(topicSelectorFormsLayout, addTopicSelectorFormButton));
	}

	private void bindData(List<SubTopic> subTopics) {
		subTopics.stream().forEach(this::addTopicSelectorForm);
	}
	
	private void createValidators() {
		// TODO Auto-generated method stub
		
	}
	
	private void addTopicSelectorForm(SubTopic subTopic) {
	    TopicSelectorForm topicSelectorForm = new TopicSelectorForm(subTopic);
	    Button removeButton = new Button(FontAwesome.CLOSE);
	    removeButton.addStyleName(ValoTheme.BUTTON_TINY);
	    removeButton.addStyleName(ValoTheme.BUTTON_DANGER);
        
	    topicSelectorForms.add(topicSelectorForm);
	    HorizontalLayout layout = new HorizontalLayout(removeButton, topicSelectorForm);
	    layout.setSpacing(true);
	    layout.setComponentAlignment(removeButton, Alignment.BOTTOM_CENTER);
	    removeButton.addClickListener(click -> {
	    	topicSelectorFormsLayout.removeComponent(layout);
	    	topicSelectorForms.remove(topicSelectorForm);
	    	addOrRemoveButtonWasCicked = true;
	    });
			
	    topicSelectorFormsLayout.addComponent(layout);
	    addOrRemoveButtonWasCicked = true;
	}
	
	public boolean isDataModified() {
		return topicSelectorForms.stream().anyMatch(TopicSelectorForm::isDataModified) || addOrRemoveButtonWasCicked;
	}
	
	public boolean isDataValid() {
		return true;
	}
	
	public List<SubTopic> getSubTopics() {
		return topicSelectorForms.stream().map(TopicSelectorForm::getSubTopic).collect(Collectors.toList());
	}
}
