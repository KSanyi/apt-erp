package apt.erp.infrastructure.ui;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.userservice.Authenticator;
import apt.erp.userservice.Authenticator.AuthenticationException;
import apt.erp.userservice.Authenticator.WrongPasswordException;
import apt.erp.userservice.User;
import apt.erp.userservice.application.DemoAuthenticator;

@SuppressWarnings("serial")
class LoginWindow extends Window {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginWindow.class);

    private final TextField userNameField = new TextField("User name:");
    private final PasswordField passwordField = new PasswordField("Password:");
    private final Button button = new Button("Log in", click -> logIn());
    
    private final Authenticator authenticator;
    
    private final Consumer<User> successFulLoginHandler;
    
    LoginWindow(Authenticator authenticator, Consumer<User> successFulLoginHandler) {
        this.authenticator = authenticator;
        this.successFulLoginHandler = successFulLoginHandler;
        
        setCaption("Loan Application Engine");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setDraggable(false);
        setWidth("300px");
        userNameField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        passwordField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        FormLayout layout = new FormLayout(userNameField, passwordField);
        layout.setMargin(true);
        setContent(layout);
        
        PopupView popup = new PopupView("Help", new UsersTable());
        layout.addComponent(popup);
        HorizontalLayout buttonBar = new HorizontalLayout(button, popup);
        buttonBar.setSizeFull();
        buttonBar.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        buttonBar.setComponentAlignment(popup, Alignment.BOTTOM_RIGHT);
        
        layout.addComponent(buttonBar);
    }

    private void logIn() {
        try {
        	User userInfo = authenticator.authenticateUser(userNameField.getValue(), passwordField.getValue());
            successFulLoginHandler.accept(userInfo);
            close();
        } catch (WrongPasswordException ex) {
        	logger.warn("Failed login attempt with wrong password with user name " + userNameField.getValue());
            Notification.show("Login failed", "Wrong user name or password", Notification.Type.ERROR_MESSAGE);
        } catch (AuthenticationException ex) {
        	logger.warn("Error during authentication with user name " + userNameField.getValue());
            Notification.show("Login failed", "Error during authentication", Notification.Type.ERROR_MESSAGE);
        }
    }
    
    private static class UsersTable extends Table {
        
        UsersTable() {
            addContainerProperty("UserId", String.class, "");
            addContainerProperty("Role", String.class, "");
            
            for(User userInfo : DemoAuthenticator.users.keySet()) {
                addItem(new Object[]{userInfo.userId, userInfo.role.toString()}, userInfo);
            }
            
            setPageLength(0);
            addStyleName(ValoTheme.TABLE_SMALL);
        }
    }
    
}
