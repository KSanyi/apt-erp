package apt.erp.translatorservice.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import apt.erp.customerservice.domain.Domain;
import apt.erp.projectservice.domain.Language;
import apt.erp.projectservice.domain.Service;

public class Translator {

    public static Translator createEmpty() {
        return new Translator(TranslatorId.newId, ContactData.createEmpty(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), "");
    }
    
    public final TranslatorId id;
    public final ContactData contactData;
    private final List<Language> languages;
    private final List<Service> services;
    private final List<Domain> domains;
    public final String comment;
    
    public Translator(TranslatorId id, ContactData contactData, List<Language> languages, List<Service> services, List<Domain> domains, String comment) {
        this.id = id;
        this.contactData = contactData;
        this.languages = languages;
        this.services = services;
        this.domains = domains;
        this.comment = comment;
    }
    
    public Translator updated(ContactData contactData, List<Language> languages, List<Service> services, List<Domain> domains, String comment) {
        return new Translator(id, contactData, languages, services, domains, comment);
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
               contactData.matches(filterPart) ||
               comment != null && comment.contains(filterPart));
    }

    @Override
    public String toString() {
        return "Name: " + contactData.name + " (" + languages + ")";
    }

    public String toDetailedString(){
        return new StringBuilder()
        .append("Id: ").append(id).append(" ")
        .append("ContactData: ").append(contactData).append(", ")
        .append("languages: ").append(languages).append(", ")
        .append("services: ").append(services).append(", ")
        .append("domains: ").append(domains).append(", ")
        .append("comment: ").append(comment).toString();
    }

}
