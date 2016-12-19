package apt.erp.translatorservice.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import apt.erp.common.demo.RandomWordPicker;
import apt.erp.common.domain.Address;
import apt.erp.common.domain.EmailAddress;
import apt.erp.common.domain.IdGenerator;
import apt.erp.common.domain.Name;
import apt.erp.common.domain.PhoneNumber;
import apt.erp.common.domain.TaxId;
import apt.erp.infrastructure.ResourceFileLoader;
import apt.erp.projectservice.domain.Language;
import apt.erp.projectservice.domain.LanguageService;
import apt.erp.projectservice.domain.LanguageServiceType;
import apt.erp.translatorservice.domain.ContactData;
import apt.erp.translatorservice.domain.InvoicingCompany;
import apt.erp.translatorservice.domain.InvoicingCompany.InvoicingType;
import apt.erp.translatorservice.domain.InvoicingData;
import apt.erp.translatorservice.domain.LanguageSkills;
import apt.erp.translatorservice.domain.PaymentInfo;
import apt.erp.translatorservice.domain.PaymentInfo.PaymentMode;
import apt.erp.translatorservice.domain.PaymentInfo.SettlementMode;
import apt.erp.translatorservice.domain.Translator;

public class DemoTranslatorFactory {

	private final RandomWordPicker lastNamePicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/LastNames.txt"));
	private final RandomWordPicker firstNamePicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/FirstNames.txt"));
	private final RandomWordPicker companyNamePicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/Companies.txt"));
	private final RandomWordPicker townPicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/Towns.txt"));
	private final RandomWordPicker streetPicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/Streets.txt"));
	
	private Random random = new Random();
	
	public Translator createRandomTranslator() {
		
		String comment = random.nextInt(10) == 0 ? "Fontos ugyfel" : "";
		
		List<Language> languages = generateLanguages();
		
		return new Translator(IdGenerator.generateTranslatorId(), generateContactData(), generateInvoicingData(), languages, generateLanguageSkills(), comment);
	}
	
	private ContactData generateContactData() {
	    Name name = new Name(lastNamePicker.pickRandomWord() + " " + firstNamePicker.pickRandomWord());
        PhoneNumber phoneNumber1 = generatePhoneNumber();
        EmailAddress emailAddress1 = generateEmailAddress(name);
        PhoneNumber phoneNumber2 = random.nextInt(10) == 0 ? generatePhoneNumber() : PhoneNumber.createEmpty();
        EmailAddress emailAddress2 = random.nextInt(10) == 0 ? generateEmailAddress(name) : EmailAddress.createEmpty();
        
        List<LanguageServiceType> serviceTypes = generateServiceTypes();
        
        return new ContactData(name, phoneNumber1, phoneNumber2, emailAddress1, emailAddress2, serviceTypes);
	}
	
    private EmailAddress generateEmailAddress(Name name) {
        String[] carriers = new String[]{"gmail.com", "fremail.hu", "hotmail.com"};
        switch(random.nextInt(2)) {
            case 0: return new EmailAddress(name.cleanName().replaceAll("\\s+","")+ "@" + carriers[random.nextInt(3)]);
            default: return new EmailAddress(name.cleanName().replaceAll("\\s+","") + random.nextInt(100) + "@" + carriers[random.nextInt(3)]);
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

    private List<Language> generateLanguages() {
        Set<Language> languages = new HashSet<>();
        languages.add(Language.Hungarian);
        int numberOfLanguages = random.nextInt(3) + 1;
        for(int i=0;i<numberOfLanguages;i++) {
            languages.add(Language.all.get(random.nextInt(Language.all.size())));
        }
        return new ArrayList<>(languages);
    }
    
    private List<LanguageServiceType> generateServiceTypes() {
        Set<LanguageServiceType> serviceTypes = new HashSet<>();
        int numberOfServiceTypes = random.nextInt(3);
        serviceTypes.add(LanguageServiceType.Translation);
        for(int i=0;i<numberOfServiceTypes;i++) {
            serviceTypes.add(LanguageServiceType.all.get(random.nextInt(LanguageServiceType.all.size())));
        }
        return serviceTypes.stream().sorted().collect(Collectors.toList());
    }
    
    private InvoicingData generateInvoicingData() {
		return new InvoicingData(generateContractDate(), generatePaymentInfo(), generateInvoicingCompany());
	}
    
	private Optional<LocalDate> generateContractDate() {
    	if(random.nextInt(5) == 0) {
    		return Optional.empty();
    	} else {
    		return Optional.of(LocalDate.now().minusDays(random.nextInt(500)));
    	}
    }
	
	private PaymentInfo generatePaymentInfo() {
		SettlementMode settlementMode = SettlementMode.values()[random.nextInt(SettlementMode.values().length)];
		PaymentMode paymentMode = PaymentMode.values()[random.nextInt(PaymentMode.values().length)];
		int paymentDeadlineDays = random.nextInt(5) + 10;
		return new PaymentInfo(settlementMode, paymentDeadlineDays, paymentMode);
	}
    
	private Optional<InvoicingCompany> generateInvoicingCompany() {
		if(random.nextInt(5) == 0) {
    		return Optional.empty();
    	} else {
    		String name = companyNamePicker.pickRandomWord();
    		TaxId  taxId = generateTaxId();
    		InvoicingType invoicingType = InvoicingType.values()[random.nextInt(InvoicingType.values().length)];
    		Address address = generateAddress();
    		boolean invoiceAddressIsTheSame = random.nextInt(10) > 0;
    		Optional<Address> invoiceAddress = invoiceAddressIsTheSame ? Optional.empty() : Optional.of(generateAddress());
    		boolean vatFree = random.nextInt(10) == 0;
    		return Optional.of(new InvoicingCompany(name, taxId, invoicingType, address, invoiceAddress, vatFree));
    	}
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
	
	private LanguageSkills generateLanguageSkills() {
		return new LanguageSkills(generateServices());
	}
	
    private List<LanguageService> generateServices() {
    	List<LanguageService> services = new ArrayList<>();
    	int numberOfServices = random.nextInt(3) + 1;
    	for(int i=0;i<numberOfServices;i++) {
    		Language language = Language.all.get(random.nextInt(Language.all.size()));
    		services.add(new LanguageService(language, Language.Hungarian, LanguageServiceType.Translation));
    		if(random.nextInt(5) != 0) services.add(new LanguageService(Language.Hungarian, language, LanguageServiceType.Translation));
    		if(random.nextInt(5) == 0) {
    			services.add(new LanguageService(language, Language.Hungarian, LanguageServiceType.Interpretation));
    			if(random.nextInt(5) != 0) services.add(new LanguageService(Language.Hungarian, language, LanguageServiceType.Interpretation));
    		}
    		if(random.nextInt(5) == 0) {
    			services.add(new LanguageService(language, Language.Hungarian, LanguageServiceType.Lectoring));
    			if(random.nextInt(5) != 0) services.add(new LanguageService(Language.Hungarian, language, LanguageServiceType.Lectoring));
    		}
    	}
    	
        return services;
    }
    
    /*
    private List<Domain> generateDomains() {
        Set<Domain> domains = new HashSet<>();
        int numberOfDomains = random.nextInt(3);
        for(int i=0;i<numberOfDomains;i++) {
            domains.add(Domain.all.get(random.nextInt(Domain.all.size())));
        }
        return new ArrayList<>(domains);
    }
    */
    
	private int createRandomDigit() {
		return random.nextInt(10);
	}
	
}
