package apt.erp.projectservice.domain;

import java.util.Arrays;
import java.util.List;

public enum Language  {

    English,
    German,
    French,
    Italian,
    Spanish,
    Portugese,
    Norvegian,
    Swedish,
    Danish,
    Finnish,
    Russian,
    Hungarian,
    Japanese,
    Other;
    
    public static List<Language> all = Arrays.asList(values());
    
}
