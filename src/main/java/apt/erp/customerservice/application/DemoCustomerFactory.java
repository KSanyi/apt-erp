package apt.erp.customerservice.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import apt.erp.common.demo.RandomWordPicker;
import apt.erp.common.domain.Address;
import apt.erp.common.domain.EmailAddress;
import apt.erp.common.domain.IdGenerator;
import apt.erp.common.domain.Name;
import apt.erp.common.domain.PhoneNumber;
import apt.erp.common.domain.TaxId;
import apt.erp.customerservice.domain.Contact;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.Domain;
import apt.erp.infrastructure.ResourceFileLoader;
import apt.erp.projectservice.domain.Language;

public class DemoCustomerFactory {

	private final RandomWordPicker companyNamePicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/Companies.txt"));
	private final RandomWordPicker lastNamePicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/LastNames.txt"));
	private final RandomWordPicker firstNamePicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/FirstNames.txt"));
	private final RandomWordPicker townPicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/Towns.txt"));
	private final RandomWordPicker streetPicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/Streets.txt"));
	
	private Random random = new Random();
	
	public CustomerData createRandomCustomerData() {
		
		Name name = new Name(companyNamePicker.pickRandomWord());
		Address address = generateAddress();
		boolean invoiceAddressIsTheSame = random.nextInt(10) > 0;
		Optional<Address> invoiceAddress = invoiceAddressIsTheSame ? Optional.empty() : Optional.of(generateAddress());
		String comment = random.nextInt(10) == 0 ? "Fontos ugyfel" : "";
		
		List<Contact> customerContacts = generateCustomerContacts();
		Domain customerDomain = generateCustomerDomain();
		Language customerLanguage = generateCustomerLanguage();
		
		return new CustomerData(IdGenerator.generateCustomerId(), generateTaxId(), name, address, invoiceAddress, comment, customerDomain, customerLanguage, customerContacts);
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

    private Domain generateCustomerDomain() {
        return Domain.all.get(random.nextInt(Domain.all.size()));
    }
    
	private Language generateCustomerLanguage() {
        return Language.all.get(random.nextInt(Language.all.size()));
    }
	
	private TaxId generateTaxId() {
        StringBuilder stringBuilder = new StringBuilder();
        IntStream.range(0, 8).forEach(i -> stringBuilder.append(createRandomDigit()));
        stringBuilder
        .append("-")
        .append(createRandomDigit())
        .append("-")
        .append(createRandomDigit())
        .append(createRandomDigit());
        
        return new TaxId(stringBuilder.toString());
    }
	
	private Address generateAddress() {
		String[] zipAndTown = townPicker.pickRandomWord().split("\t");
		String zip = zipAndTown[0];
		String town = zipAndTown[1];
		String[] streetTypes = new String[]{"utca", "út", "tér"};
		String street = streetPicker.pickRandomWord() + " " + streetTypes[random.nextInt(3)];
		String number = String.valueOf(random.nextInt(100));
		return new Address(zip, town, street, number);	
	}
	
	private int createRandomDigit() {
		return random.nextInt(10);
	}
	
}
