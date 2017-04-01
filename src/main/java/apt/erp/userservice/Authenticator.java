package apt.erp.userservice;

public interface Authenticator {

    User authenticateUser(String userName, String password) throws AuthenticationException;
    
    @SuppressWarnings("serial")
    public static class AuthenticationException extends Exception {
        public AuthenticationException(String errorMessage) {
            super(errorMessage);
        }
    }
    
    @SuppressWarnings("serial")
    public static class WrongPasswordException extends AuthenticationException {
        public WrongPasswordException() {
            super("Wrong user name or password");
        }
    }
    
}
