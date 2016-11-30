package apt.erp.translatorservice.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import apt.erp.common.domain.EmailAddress;
import apt.erp.common.domain.Name;
import apt.erp.common.domain.PhoneNumber;
import apt.erp.customerservice.domain.Domain;
import apt.erp.projectservice.domain.Language;
import apt.erp.projectservice.domain.Service;

public class Translator {

    public static Translator createEmpty() {
        return new Translator(TranslatorId.newId, Name.createEmptyName(), PhoneNumber.createEmptyPhoneNumber(), EmailAddress.createEmptyEmailAddress(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), "");
    }
    
    public final TranslatorId id;
    public final Name name;
    public final PhoneNumber phoneNumber;
    public final EmailAddress emailAddress;
    private final List<Language> languages;
    private final List<Service> services;
    private final List<Domain> domains;
    public final String comment;
    
    public Translator(TranslatorId id, Name name, PhoneNumber phoneNumber, EmailAddress emailAddress, List<Language> languages, List<Service> services, List<Domain> domains, String comment) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.languages = languages;
        this.services = services;
        this.domains = domains;
        this.comment = comment;
    }
    
    public Translator updated(Name name, PhoneNumber phoneNumber, EmailAddress emailAddress, List<Language> languages, List<Service> services, List<Domain> domains, String comment) {
        return new Translator(id, name, phoneNumber, emailAddress, languages, services, domains, comment);
    }
    
    public List<Language> languages() {
        return new ArrayList<>(languages);
    }
    
    public List<Service> services() {
        return new ArrayList<>(services);
    }
    
    public List<Domain> domains() {
        return new ArrayList<>(domains);
    }
    
    public boolean matches(String filter) {
        String[] filterParts = filter.split(" ");
        return Stream.of(filterParts).allMatch(filterPart -> 
               id.matches(filterPart) ||
               name.matches(filterPart) ||
               comment != null && comment.contains(filterPart));
    }

    @Override
    public String toString() {
        return "Name: " + name + " (" + languages + ")";
    }

    public String toDetailedString(){
        return new StringBuilder()
        .append("Id: ").append(id).append(" ")
        .append("Name: ").append(name).append(", ")
        .append("Phone: ").append(phoneNumber).append(", ")
        .append("Email: ").append(emailAddress).append(", ")
        .append("languages: ").append(languages).append(", ")
        .append("services: ").append(services).append(", ")
        .append("domains: ").append(domains).append(", ")
        .append("comment: ").append(comment).toString();
    }

}
