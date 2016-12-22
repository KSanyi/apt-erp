package apt.erp.common.vaadin;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public interface LayoutFactory {

	static VerticalLayout createCenteredVerticalLayout(Component ... components){
		VerticalLayout layout = createCenteredVerticalLayoutWithNoSpacing(components);
		layout.setSpacing(true);
		return layout;
	}
	
	static VerticalLayout createCenteredVerticalLayoutWithNoSpacing(Component ... components){
		VerticalLayout layout = createCenteredVerticalLayoutWithNoSpacingNoMargin(components);
		layout.setMargin(true);
		return layout; 
	}
	
	static VerticalLayout createCenteredVerticalLayoutWithNoSpacingNoMargin(Component ... components){
		VerticalLayout layout = new VerticalLayout();
		layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		layout.addComponents(components);
		return layout;
	}
	
	static VerticalLayout createVerticalLayout(Component ... components){
		VerticalLayout layout = createVerticalLayoutWithNoSpacing(components);
		layout.setSpacing(true);
		return layout;
	}
	
	static VerticalLayout createVerticalLayoutWithNoMargin(Component ... components){
        VerticalLayout layout = new VerticalLayout(components);
        layout.setSpacing(true);
        return layout;
    }
	
	static VerticalLayout createVerticalLayoutWithNoSpacing(Component ... components){
		VerticalLayout layout = new VerticalLayout(components);
		layout.setMargin(true);
		return layout;
	}
	
	static HorizontalLayout createHorizontalLayout(Component ... components){
		HorizontalLayout layout = new HorizontalLayout(components);
		layout.setSpacing(true);
		return layout;
	}
	
	static HorizontalLayout createHorizontalLayoutWithNoSpacing(Component ... components){
		HorizontalLayout layout = new HorizontalLayout(components);
		layout.setMargin(true);
		return layout;
	}
	
	static HorizontalLayout createHorizontalLayoutWithMargin(Component ... components){
		HorizontalLayout layout = new HorizontalLayout(components);
		layout.setSpacing(true);
		layout.setMargin(true);
		return layout;
	}
	
}
