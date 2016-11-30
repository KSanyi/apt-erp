package apt.erp.translatorservice.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import apt.erp.common.demo.RandomWordPicker;
import apt.erp.common.domain.EmailAddress;
import apt.erp.common.domain.IdGenerator;
import apt.erp.common.domain.Name;
import apt.erp.common.domain.PhoneNumber;
import apt.erp.customerservice.domain.Domain;
import apt.erp.infrastructure.ResourceFileLoader;
import apt.erp.projectservice.domain.Language;
import apt.erp.projectservice.domain.Service;
import apt.erp.translatorservice.domain.Translator;

public class DemoTranslatorFactory {

	private final RandomWordPicker lastNamePicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/LastNames.txt"));
	private final RandomWordPicker firstNamePicker = new RandomWordPicker(ResourceFileLoader.loadPath("demodata/FirstNames.txt"));
	
	private Random random = new Random();
	
	public Translator createRandomTranslator() {
		
	    Name name = new Name(lastNamePicker.pickRandomWord() + " " + firstNamePicker.pickRandomWord());
	    EmailAddress emailAddress = generateEmailAddress(name);
        PhoneNumber phoneNumber = generatePhoneNumber();
		String comment = random.nextInt(10) == 0 ? "Fontos ugyfel" : "";
		
		List<Language> languages = generateLanguages();
		List<Domain> domains = generateDomains();
		List<Service> services = generateServices(languages);
		
		return new Translator(IdGenerator.generateTranslatorId(), name, phoneNumber, emailAddress, languages, services, domains, comment);
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

    private List<Domain> generateDomains() {
        Set<Domain> domains = new HashSet<>();
        int numberOfDomains = random.nextInt(3);
        for(int i=0;i<numberOfDomains;i++) {
            domains.add(Domain.all.get(random.nextInt(Domain.all.size())));
        }
        return new ArrayList<>(domains);
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
    
    private List<Service> generateServices(List<Language> languages) {
        
        return Collections.emptyList();
    }
    
	private int createRandomDigit() {
		return random.nextInt(10);
	}
	
}
