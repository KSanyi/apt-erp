package apt.erp.customerservice.application;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import apt.erp.common.demo.RandomWordPicker;
import apt.erp.customerservice.domain.Address;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerId;
import apt.erp.customerservice.domain.Domain;
import apt.erp.customerservice.domain.Name;
import apt.erp.infrastructure.ResourceFileLoader;

public class DemoCustomerFactory {

	private final RandomWordPicker lastNamePicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/LastNames.txt"));
	private final RandomWordPicker firstNamePicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/FirstNames.txt"));
	private final RandomWordPicker townPicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/Towns.txt"));
	private final RandomWordPicker streetPicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/Streets.txt"));
	
	private final List<Domain> domains = Arrays.asList(
	        new Domain("FI", "Finance"),
	        new Domain("HC", "Healthcare"),
	        new Domain("IN", "Industry"),
	        new Domain("AG", "Agreeculture"),
	        new Domain("IT", "IT"),
	        new Domain("SP", "Sport"),
	        new Domain("TR", "Transport"),
	        new Domain("HI", "History"));
	
	private Random random = new Random();
	
	public CustomerData createRandomCustomerData() {
		
		Name name = new Name(lastNamePicker.pickRandomWord() + " " + firstNamePicker.pickRandomWord());
		Address address = generateRandomAddress();
		Address invoiceAddress = null;
		boolean invoiceAddressIsTheSame = random.nextInt(10) > 0;
		if(!invoiceAddressIsTheSame) {
			invoiceAddress = generateRandomAddress();
		}
		String comment = random.nextInt(10) == 0 ? "Fontos ugyfel" : "";
		
		Domain[] customerDomains = generateCustomerDomains();
		
		return new CustomerData(generateCustomerId(), name, address, invoiceAddressIsTheSame, invoiceAddress, comment, customerDomains);
	}
	
	private Domain[] generateCustomerDomains() {
	    Set<Domain> customerDomains = new HashSet<>();
	    customerDomains.add(domains.get(random.nextInt(domains.size())));
	    if(random.nextInt(10) == 0) {
	        customerDomains.add(domains.get(random.nextInt(domains.size())));
	    }
	    return customerDomains.toArray(new Domain[0]);
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
	
	private char createRandomChar() {
		return (char)('A' + random.nextInt(26));
	}
	
	private int createRandomDigit() {
		return random.nextInt(10);
	}
	
}
