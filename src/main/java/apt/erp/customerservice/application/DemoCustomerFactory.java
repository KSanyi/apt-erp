package apt.erp.customerservice.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import apt.erp.common.demo.RandomWordPicker;
import apt.erp.customerservice.domain.Address;
import apt.erp.customerservice.domain.Contact;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerId;
import apt.erp.customerservice.domain.Domain;
import apt.erp.customerservice.domain.EmailAddress;
import apt.erp.customerservice.domain.Name;
import apt.erp.customerservice.domain.PhoneNumber;
import apt.erp.infrastructure.ResourceFileLoader;
import apt.erp.projectservice.domain.Language;

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
		boolean invoiceAddressIsTheSame = random.nextInt(10) > 0;
		Optional<Address> invoiceAddress = invoiceAddressIsTheSame ? Optional.empty() : Optional.of(generateRandomAddress());
		String comment = random.nextInt(10) == 0 ? "Fontos ugyfel" : "";
		
		List<Contact> customerContacts = generateCustomerContacts();
		List<Domain> customerDomains = generateCustomerDomains();
		List<Language> customerLanguages = generateCustomerLanguages();
		
		return new CustomerData(generateCustomerId(), name, address, invoiceAddress, comment, customerContacts, customerDomains, customerLanguages);
	}
	
	private List<Contact> generateCustomerContacts() {
	    List<Contact> customerContacts = new ArrayList<>();
        customerContacts.add(generateCustomerContact());
        if(random.nextInt(10) == 0) {
            customerContacts.add(generateCustomerContact());
        }
        return customerContacts;
    }
	
	private Contact generateCustomerContact() {
	    Name name = new Name(lastNamePicker.pickRandomWord() + " " + firstNamePicker.pickRandomWord());
	    EmailAddress emailAddress = generateEmailAddress(name);
	    PhoneNumber phoneNumber = generatePhoneNumber();
	    return new Contact(name, phoneNumber, emailAddress);
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

    private List<Domain> generateCustomerDomains() {
        Set<Domain> customerDomains = new HashSet<>();
        customerDomains.add(domains.get(random.nextInt(domains.size())));
        if(random.nextInt(10) == 0) {
            customerDomains.add(domains.get(random.nextInt(domains.size())));
        }
        return new ArrayList<>(customerDomains);
    }
    
	private List<Language> generateCustomerLanguages() {
        Set<Language> customerLanguages = new HashSet<>();
        List<Language> languages = Language.languages;
        customerLanguages.add(languages.get(random.nextInt(languages.size())));
        if(random.nextInt(10) == 0) {
            customerLanguages.add(languages.get(random.nextInt(languages.size())));
        }
        return new ArrayList<>(customerLanguages);
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
