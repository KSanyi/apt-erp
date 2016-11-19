package apt.erp.customerservice.application;

import java.util.Random;

import apt.erp.common.demo.RandomWordPicker;
import apt.erp.customerservice.domain.Address;
import apt.erp.customerservice.domain.Customer;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerId;
import apt.erp.customerservice.domain.EmailAddress;
import apt.erp.customerservice.domain.Name;
import apt.erp.customerservice.domain.PhoneNumber;
import apt.erp.infrastructure.ResourceFileLoader;

public class DemoCustomerFactory {

	private final RandomWordPicker lastNamePicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/LastNames.txt"));
	private final RandomWordPicker firstNamePicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/FirstNames.txt"));
	private final RandomWordPicker townPicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/Towns.txt"));
	private final RandomWordPicker streetPicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/Streets.txt"));
	
	private Random random = new Random();
	
	public Customer createRandomCustomer() {
		
		Name name = new Name(lastNamePicker.pickRandomWord() + " " + firstNamePicker.pickRandomWord());
		Address address = generateRandomAddress();
		Address invoiceAddress = null;
		boolean invoiceAddressIsTheSame = random.nextInt(10) > 0;
		if(!invoiceAddressIsTheSame) {
			invoiceAddress = generateRandomAddress();
		}
		EmailAddress emailAddress = generateEmailAddress(name);
		PhoneNumber phoneNumber = generatePhoneNumber();
		String comment = random.nextInt(10) == 0 ? "Fontos ugyfel" : "";
		
		CustomerData customerData = new CustomerData(name, address, invoiceAddressIsTheSame, invoiceAddress, emailAddress, phoneNumber, comment);
		return new Customer(generateCustomerId(), customerData);
	}
	
	private CustomerId generateCustomerId() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
		.append(createRandomChar())
		.append(createRandomChar())
		.append(createRandomChar())
		.append(createRandomDigit())
		.append(createRandomDigit())
		.append(createRandomDigit());
		
		return new CustomerId(stringBuilder.toString());
	}
	
	private Address generateRandomAddress() {
		String[] zipAndTown = townPicker.pickRandomWord().split("\t");
		String zip = zipAndTown[0];
		String town = zipAndTown[1];
		String[] streetTypes = new String[]{"utca", "út", "tér"};
		String street = streetPicker.pickRandomWord() + " " + streetTypes[random.nextInt(3)];
		String number = String.valueOf(random.nextInt(100));
		return new Address(zip, town, street, number);	
	}
	
	private EmailAddress generateEmailAddress(Name name) {
		String[] carriers = new String[]{"gmail.com", "fremail.hu", "hotmail.com"};
		switch(random.nextInt(3)) {
			case 0: return new EmailAddress(name.cleanName().replaceAll("\\s+","")+ "@" + carriers[random.nextInt(3)]);
			case 1: return new EmailAddress(name.cleanName().replaceAll("\\s+","") + random.nextInt(100) + "@" + carriers[random.nextInt(3)]);
			default: return new EmailAddress("");
		}
	}
	
	private PhoneNumber generatePhoneNumber() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("+36");
		String[] carriers = new String[]{"20", "30", "70"};
		stringBuilder.append(carriers[random.nextInt(3)]);
		for(int i=0;i<7;i++){
			stringBuilder.append(createRandomDigit());	
		}
		return new PhoneNumber(stringBuilder.toString());
	}
	
	private char createRandomChar() {
		return (char)('A' + random.nextInt(26));
	}
	
	private int createRandomDigit() {
		return random.nextInt(10);
	}
	
}
