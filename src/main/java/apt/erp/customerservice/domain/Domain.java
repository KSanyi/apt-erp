package apt.erp.customerservice.domain;

import java.util.Arrays;
import java.util.List;

public enum Domain {

    Finance,
    Healthcare,
    Construction,
    Agreculture,
    IT,
    Sport,
    Transport,
    Science,
    Other;
    
    public static List<Domain> all = Arrays.asList(values());    
}
