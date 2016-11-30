package apt.erp.common.domain;

import java.util.Random;

import apt.erp.customerservice.domain.CustomerId;
import apt.erp.translatorservice.domain.TranslatorId;

public class IdGenerator {

	private static Random random = new Random();

	public static CustomerId generateCustomerId() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("C").append(createRandomChar()).append(createRandomChar())
				.append(createRandomDigit()).append(createRandomDigit()).append(createRandomDigit());

		return new CustomerId(stringBuilder.toString());
	}
	
	private static char createRandomChar() {
		return (char) ('A' + random.nextInt(26));
	}

	private static int createRandomDigit() {
		return random.nextInt(10);
	}

    public static TranslatorId generateTranslatorId() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("T").append(createRandomChar()).append(createRandomChar())
                .append(createRandomDigit()).append(createRandomDigit()).append(createRandomDigit());

        return new TranslatorId(stringBuilder.toString());
    }

}
