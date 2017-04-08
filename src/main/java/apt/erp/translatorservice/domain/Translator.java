package apt.erp.translatorservice.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import apt.erp.projectservice.domain.Language;

public class Translator {

    public static Translator createEmpty() {
        return new Translator(TranslatorId.newId, PersonalData.createEmpty(), InvoicingData.createEmpty(),
                LanguageSkills.empty, Collections.emptyList(), "");
    }
    
    public final TranslatorId id;
    public final PersonalData personalData;
    public final InvoicingData invoicingData;
    private final List<Document> documents;
    public final LanguageSkills languageSkills;
    public final String comment;
    
    public Translator(TranslatorId id, PersonalData personalData, InvoicingData invoicingData, LanguageSkills languageSkills, List<Document> documents, String comment) {
        this.id = id;
        this.personalData = personalData;
        this.invoicingData = invoicingData;
        this.languageSkills = languageSkills;
        this.documents = documents;
        this.comment = comment;
    }
    
    public Translator updated(PersonalData personalData, InvoicingData invoicingData, LanguageSkills languageSkills, List<Document> documents, String comment) {
        return new Translator(id, personalData, invoicingData, languageSkills, documents, comment);
    }
    
    public List<Language> languages() {
    	return languageSkills.languages();
    }
    
    public List<Document> documents() {
        return new ArrayList<>(documents);
    }
    
    public boolean matches(String filter) {
        String[] filterParts = filter.split(" ");
        return Stream.of(filterParts).allMatch(filterPart -> 
               id.matches(filterPart) ||
               personalData.matches(filterPart) ||
               comment != null && comment.contains(filterPart));
    }

    @Override
    public String toString() {
        return "Name: " + personalData.name + " (" + languages() + ")";
    }

    public String toDetailedString(){
        return new StringBuilder()
        .append("Id: ").append(id).append(" ")
        .append("PersonalData: ").append(personalData).append(", ")
        .append("language skills: ").append(languageSkills).append(", ")
        .append("comment: ").append(comment).toString();
    }

}
