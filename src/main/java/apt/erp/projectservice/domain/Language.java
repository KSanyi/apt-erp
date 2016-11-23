package apt.erp.projectservice.domain;

import java.util.Arrays;
import java.util.List;

import apt.erp.common.SimpleValueObject;

public class Language extends SimpleValueObject {

    public static List<Language> languages = Arrays.asList(
            new Language("Angol"),
            new Language("Német"),
            new Language("Francia"),
            new Language("Orosz"),
            new Language("Magyar"),
            new Language("Olasz"),
            new Language("Portugál"),
            new Language("Holland"),
            new Language("Svéd"),
            new Language("Norvég"),
            new Language("Finn"),
            new Language("Dán"),
            new Language("Spanyol"));
    
    public Language(String value) {
        super(value);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && getClass() == other.getClass();
    }
    
}
