package apt.erp.userservice.application;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import apt.erp.userservice.Authenticator;
import apt.erp.userservice.User;
import apt.erp.userservice.UserRole;

public class DemoAuthenticator implements Authenticator {

    @Override
    public User authenticateUser(String loginName, String password) throws AuthenticationException {
        
    	User userInfo = users.keySet().stream()
                .filter(u -> u.userId.equals(loginName))
                .findAny().orElseThrow(() -> new WrongPasswordException());
        
        if(password.equals(users.get(userInfo))) {
            return userInfo;
        }
        
        throw new WrongPasswordException();
    }
    
    public static final Map<User, String> users;
    
    static {
        Map<User, String> map = new HashMap<>();
        map.put(new User("tpalasti", "Tibot Palásti", UserRole.Manager), "xxx");
        map.put(new User("jkerekes", "Judir Kerekes", UserRole.Manager), "xxx");
        map.put(new User("skocso", "Kócsó Sándor", UserRole.Admin), "xxx");
        users = Collections.unmodifiableMap(map);
    }

}
