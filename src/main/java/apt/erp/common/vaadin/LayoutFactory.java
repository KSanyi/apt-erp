package apt.erp.common.vaadin;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public interface LayoutFactory {

	static VerticalLayout createSimpleVerticalLayout(Component ... components){
		VerticalLayout layout = new VerticalLayout(components);
		layout.setSpacing(false);
		layout.setMargin(false);
		return layout;
	}
	
	static VerticalLayout createCenteredVerticalLayout(Component ... components){
		VerticalLayout layout = new VerticalLayout(components);
		layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		return layout;
	}
	
	static VerticalLayout createCenteredVerticalLayoutWithNoSpacing(Component ... components){
		VerticalLayout layout = createCenteredVerticalLayoutWithNoSpacingNoMargin(components);
		return layout; 
	}
	
	static VerticalLayout createCenteredVerticalLayoutWithNoSpacingNoMargin(Component ... components){
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(false);
		layout.setMargin(false);
		layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		layout.addComponents(components);
		return layout;
	}
	
	static VerticalLayout createVerticalLayout(Component ... components){
		VerticalLayout layout = createVerticalLayoutWithNoSpacing(components);
		return layout;
	}
	
	static VerticalLayout createVerticalLayoutWithNoMargin(Component ... components){
        VerticalLayout layout = new VerticalLayout(components);
        layout.setMargin(false);
        return layout;
    }
	
	static VerticalLayout createVerticalLayoutWithNoSpacing(Component ... components){
		VerticalLayout layout = new VerticalLayout(components);
		layout.setSpacing(false);
		return layout;
	}
	
	static HorizontalLayout createHorizontalLayout(Component ... components){
		HorizontalLayout layout = new HorizontalLayout(components);
		return layout;
	}
	
	static HorizontalLayout createHorizontalLayoutWithNoSpacing(Component ... components){
		HorizontalLayout layout = new HorizontalLayout(components);
		layout.setMargin(true);
		layout.setSpacing(false);
		return layout;
	}
	
	static HorizontalLayout createHorizontalLayoutWithMargin(Component ... components){
		HorizontalLayout layout = new HorizontalLayout(components);
		layout.setMargin(true);
		return layout;
	}
	
}
