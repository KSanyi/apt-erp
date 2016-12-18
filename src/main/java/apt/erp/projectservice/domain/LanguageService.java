package apt.erp.projectservice.domain;

public class LanguageService {

    public final Language sourceLanguage;
    
    public final Language destLanguage;

    public final ServiceType serviceType;
    
    public LanguageService(Language sourceLanguage, Language destLanguage, ServiceType servicetype) {
        this.sourceLanguage = sourceLanguage;
        this.destLanguage = destLanguage;
        this.serviceType = servicetype;
    }
    
}
