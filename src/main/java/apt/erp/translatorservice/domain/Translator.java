package apt.erp.translatorservice.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import apt.erp.projectservice.domain.Language;

public class Translator {

    public static Translator createEmpty() {
        return new Translator(TranslatorId.newId, ContactData.createEmpty(), InvoicingData.createEmpty(),
                Collections.emptyList(), LanguageSkills.empty, "");
    }
    
    public final TranslatorId id;
    public final ContactData contactData;
    public final InvoicingData invoicingData;
    private final List<Language> languages;
    public final LanguageSkills languageSkills;
    public final String comment;
    
    public Translator(TranslatorId id, ContactData contactData, InvoicingData invoicingData, List<Language> languages, LanguageSkills languageSkills, String comment) {
        this.id = id;
        this.contactData = contactData;
        this.invoicingData = invoicingData;
        this.languages = languages;
        this.languageSkills = languageSkills;
        this.comment = comment;
    }
    
    public Translator updated(ContactData contactData, InvoicingData invoicingData, List<Language> languages, LanguageSkills languageSkills, String comment) {
        return new Translator(id, contactData, invoicingData, languages, languageSkills, comment);
    }
    
    public List<Language> languages() {
        return new ArrayList<>(languages);
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
        .append("comment: ").append(comment).toString();
    }

}
