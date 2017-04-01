package apt.erp.userservice;

public class User {

	public final String userId;
	public final String name;
	public final UserRole role;

	public User(String userId, String name, UserRole role) {
		this.userId = userId;
		this.name = name;
		this.role = role;
	}
	
}
