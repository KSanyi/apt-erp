package apt.erp.infrastructure.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.userservice.User;

public class Header extends HorizontalLayout {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(Header.class);

	private final Button logoutButton = new Button("Kijelentkezés", click -> logout());

	private final User user;

	public Header(User user) {
	    
		this.user = user;
		setSizeFull();
		setHeight("135px");
		setMargin(true);
		setStyleName("dark");

		Layout logoLayout = createLogoLayout();
		Layout userInfoLayout = createUserInfoLayout();
		addComponents(logoLayout, userInfoLayout);
		setComponentAlignment(userInfoLayout, Alignment.TOP_RIGHT);
	}

	private Layout createLogoLayout() {
		Label logo = new Label("APT-ERP");
		logo.setStyleName("mainLogo");
		
		Label subLogo = new Label("by Kocso IT Solutions KFT");
		subLogo.setStyleName("subLogo");
		
		return LayoutFactory.createSimpleVerticalLayout(logo, subLogo);
	}

	private Layout createUserInfoLayout() {
		Label userLabel = new Label(user.name);
		userLabel.addStyleName(ValoTheme.LABEL_COLORED);

		logoutButton.setStyleName(ValoTheme.BUTTON_TINY);
		logoutButton.setIcon(VaadinIcons.USER);
		
		return new HorizontalLayout(logoutButton, userLabel);
	}

	public void logout() {
		logger.info("User " + user.name + " logged out");
		UI.getCurrent().getPage().setLocation("/");
		UI.getCurrent().getSession().close();
	}
}

