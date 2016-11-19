package apt.erp.common.basic;

public interface StringUtil {

	public static boolean containsIgnoreCase(String container, String pattern) {
		return container.toLowerCase().contains(pattern.toLowerCase());
	}
	
}
