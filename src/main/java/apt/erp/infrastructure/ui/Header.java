package apt.erp.infrastructure.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import apt.erp.userservice.User;

public class Header extends HorizontalLayout implements Button.ClickListener {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(Header.class);

	private final Button logoutButton = new Button("Log out", this);

	private final ErpUI application = (ErpUI)UI.getCurrent();
	
	private final User user;

	public Header(User user) {
		this.user = user;
		setSizeFull();
		setSpacing(true);
		setStyleName(Reindeer.LAYOUT_BLACK);

		Layout logoLayout = createLogoLayout();
		addComponent(logoLayout);

		Layout userInfoLayout = createUserInfoLayout();

		VerticalLayout rightLayout = new VerticalLayout();
		rightLayout.setWidth("300px");
		rightLayout.setHeight("100%");
		rightLayout.setMargin(true);
		rightLayout.addComponent(userInfoLayout);
		addComponent(rightLayout);
		setComponentAlignment(rightLayout, Alignment.TOP_RIGHT);
	}

	private Layout createLogoLayout() {
		VerticalLayout wrapper = new VerticalLayout();
		wrapper.setSpacing(true);
		wrapper.setMargin(new MarginInfo(true, false, false, false));
		wrapper.setSizeUndefined();

		VerticalLayout logoLayout = new VerticalLayout();
		logoLayout.setMargin(true);

		HorizontalLayout firstLine = new HorizontalLayout();
		firstLine.setSpacing(true);

		Label logo = new Label("APT-ERP");
		logo.setStyleName("mainLogo");
		
		firstLine.addComponent(logo);
		
		Label subLogo = new Label("by Kocso IT Solutions KFT");
		subLogo.setStyleName("subLogo");
		logoLayout.addComponent(firstLine);
		logoLayout.addComponent(subLogo);
		
		wrapper.addComponent(logoLayout);

		return wrapper;
	}

	private Layout createUserInfoLayout() {
		VerticalLayout userInfoLayout = new VerticalLayout();
		userInfoLayout.setSizeUndefined();
		HorizontalLayout userInfoInnerLayout = new HorizontalLayout();
		userInfoInnerLayout.setSpacing(true);

		com.vaadin.server.ThemeResource userPicRes = new com.vaadin.server.ThemeResource("../runo/icons/16/user.png");
		Embedded userPic = new Embedded(null, userPicRes);
		userPic.setSizeUndefined();

		Label userLabel = new Label();
		userLabel.setSizeUndefined();
		userLabel.setCaption(user.name);
		userLabel.setStyleName("boldblue");

		VerticalLayout buttonsLayout = new VerticalLayout();
		buttonsLayout.setSizeUndefined();
		buttonsLayout.setSpacing(true);

		logoutButton.setStyleName(Reindeer.BUTTON_SMALL);
		logoutButton.setWidth("80px");
		buttonsLayout.addComponent(logoutButton);
		buttonsLayout.setWidth("80px");
		userInfoInnerLayout.addComponent(buttonsLayout);
		userInfoInnerLayout.addComponent(userPic);
		userInfoInnerLayout.addComponent(userLabel);
		userInfoLayout.addComponent(userInfoInnerLayout);

		return userInfoLayout;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton() == logoutButton) {
			logger.info("User logged out");
			application.close();
		} 
	}
}

