package apt.erp.projectservice.domain;

import java.util.Arrays;
import java.util.List;

public enum ServiceType {

    Translation("Fordítás"), Interpreting("Tolmácskodás"), Lectoring("Lektorálás"), 
    MotherTangueLectoring("Anyanyelvi lektorálás"), Audit("Átolvasás");
    
    private final String caption;
    
    ServiceType(String caption) {
        this.caption = caption;
    }
    
    public static List<ServiceType> all = Arrays.asList(values());
    
    @Override
    public String toString() {
        return caption;
    }
    
}
