package apt.erp.translatorservice.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import apt.erp.projectservice.domain.SubTopic;
import apt.erp.projectservice.domain.Topic;
import apt.erp.translatorservice.domain.ContactData;
import apt.erp.translatorservice.domain.ContactData.CommunicationChannel;
import apt.erp.translatorservice.domain.Document;
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
		
		return new Translator(IdGenerator.generateTranslatorId(), generateContactData(), generateInvoicingData(), languages, generateLanguageSkills(), generateDocuments(), comment);
	}
	
    private ContactData generateContactData() {
	    Name name = new Name(lastNamePicker.pickRandomWord() + " " + firstNamePicker.pickRandomWord());
        PhoneNumber phoneNumber1 = generatePhoneNumber();
        EmailAddress emailAddress1 = generateEmailAddress(name);
        PhoneNumber phoneNumber2 = random.nextInt(10) == 0 ? generatePhoneNumber() : PhoneNumber.createEmpty();
        EmailAddress emailAddress2 = random.nextInt(10) == 0 ? generateEmailAddress(name) : EmailAddress.createEmpty();
        String skypeId = generateSkypeId(name);
        CommunicationChannel preferredCommunicationChannel = randomEnumValue(CommunicationChannel.class);
        List<LanguageServiceType> serviceTypes = generateServiceTypes();
        
        return new ContactData(name, phoneNumber1, phoneNumber2, emailAddress1, emailAddress2, skypeId, preferredCommunicationChannel, serviceTypes);
	}
	
    private EmailAddress generateEmailAddress(Name name) {
        String[] carriers = new String[]{"gmail.com", "fremail.hu", "hotmail.com"};
        switch(random.nextInt(2)) {
            case 0: return new EmailAddress(name.cleanName().replaceAll("\\s+","")+ "@" + carriers[random.nextInt(3)]);
            default: return new EmailAddress(name.cleanName().replaceAll("\\s+","") + random.nextInt(100) + "@" + carriers[random.nextInt(3)]);
        }
    }
    
    private String generateSkypeId(Name name) {
        return name.cleanName().replaceAll("\\s+","") + random.nextInt(100);
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
        int numberOfLanguages = randomInt(1, 3);
        for(int i=0;i<numberOfLanguages;i++) {
            languages.add(randomEnumValue(Language.class));
        }
        return new ArrayList<>(languages);
    }
    
    private List<LanguageServiceType> generateServiceTypes() {
        Set<LanguageServiceType> serviceTypes = new HashSet<>();
        int numberOfServiceTypes = random.nextInt(3);
        serviceTypes.add(LanguageServiceType.Translation);
        for(int i=0;i<numberOfServiceTypes;i++) {
            serviceTypes.add(randomEnumValue(LanguageServiceType.class));
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
    		return Optional.of(generateRecentDate());
    	}
    }
	
	private PaymentInfo generatePaymentInfo() {
		SettlementMode settlementMode = randomEnumValue(SettlementMode.class);
		PaymentMode paymentMode = randomEnumValue(PaymentMode.class);
		int paymentDeadlineDays = randomInt(10, 20);
		return new PaymentInfo(settlementMode, paymentDeadlineDays, paymentMode);
	}
    
	private Optional<InvoicingCompany> generateInvoicingCompany() {
		if(random.nextInt(5) == 0) {
    		return Optional.empty();
    	} else {
    		String name = companyNamePicker.pickRandomWord();
    		TaxId  taxId = generateTaxId();
    		InvoicingType invoicingType = randomEnumValue(InvoicingType.class);
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
		String number = String.valueOf(randomInt(1, 200));
		return new Address(zip, town, street, number);	
	}
	
	private LanguageSkills generateLanguageSkills() {
		return new LanguageSkills(generateServices(), generateSubTopics());
	}
	
    private List<LanguageService> generateServices() {
    	List<LanguageService> services = new ArrayList<>();
    	int numberOfServices = random.nextInt(3) + 1;
    	for(int i=0;i<numberOfServices;i++) {
    		Language language = randomEnumValue(Language.class);
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
    
    private List<SubTopic> generateSubTopics() {
    	List<SubTopic> subTopics = new ArrayList<>();
    	int numberOfSubTopics = random.nextInt(5) + 1;
    	for(int i=0;i<numberOfSubTopics;i++) {
    		Topic topic = Topic.allTopics.get(random.nextInt(Topic.allTopics.size()));
    		SubTopic subTopic = topic.subTopics().get(random.nextInt(topic.subTopics().size()));
    		subTopics.add(subTopic);
    	}
    	return subTopics;
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
    
    private List<Document> generateDocuments() {
        List<Document> docs = new ArrayList<>();
        int numberOfDocs = randomInt(0, 10);
        for(int i=0;i<numberOfDocs;i++) {
            Document.Type docType = randomEnumValue(Document.Type.class);
            docs.add(new Document(docType, docType.toString() + ".doc", generateRecentDateTime()));
        }
        return docs;
    }
    
    private LocalDate generateRecentDate() {
        return LocalDate.now().minusDays(random.nextInt(500));
    }
    
    private LocalDateTime generateRecentDateTime() {
        return LocalDateTime.now().minusHours(random.nextInt(500 * 25));
    }
    
    private int randomInt(int minInclusive, int maxInclusive) {
        return random.nextInt(maxInclusive - minInclusive + 1) + minInclusive;
    }
    
	private int createRandomDigit() {
		return random.nextInt(10);
	}
	
	private <T> T randomEnumValue(Class<T> enumClass) {
	    return enumClass.getEnumConstants()[random.nextInt(enumClass.getEnumConstants().length)];
	}
	
}
