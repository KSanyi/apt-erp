package apt.erp.projectservice.domain;

import java.util.Arrays;
import java.util.List;

public enum LanguageServiceType {

    Translation("Fordítás"), Interpretation("Tolmácskodás"), Lectoring("Lektorálás"), 
    MotherTangueLectoring("Anyanyelvi lektorálás"), Audit("Átolvasás");
    
    private final String caption;
    
    LanguageServiceType(String caption) {
        this.caption = caption;
    }
    
    public static List<LanguageServiceType> all = Arrays.asList(values());
    
    @Override
    public String toString() {
        return caption;
    }
    
}
