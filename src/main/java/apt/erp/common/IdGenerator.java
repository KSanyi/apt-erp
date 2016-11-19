package apt.erp.common;

import java.util.Random;

import apt.erp.customerservice.domain.CustomerId;

public class IdGenerator {

	private static Random random = new Random();

	public static CustomerId generateCustomerId() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("C").append(createRandomChar()).append(createRandomChar())
				.append(createRandomDigit()).append(createRandomDigit()).append(createRandomDigit());

		return new CustomerId(stringBuilder.toString());
	}
	
	public static String generateProductCategoryId() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("PC").append(createRandomChar())
				.append(createRandomDigit()).append(createRandomDigit()).append(createRandomDigit());

		return stringBuilder.toString();
	}

	private static char createRandomChar() {
		return (char) ('A' + random.nextInt(26));
	}

	private static int createRandomDigit() {
		return random.nextInt(10);
	}

}
