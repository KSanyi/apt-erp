package apt.erp.projectservice.domain;

public class LanguageService {

    public final Language sourceLanguage;
    
    public final Language destLanguage;

    public final LanguageServiceType serviceType;
    
    public LanguageService(Language sourceLanguage, Language destLanguage, LanguageServiceType servicetype) {
        this.sourceLanguage = sourceLanguage;
        this.destLanguage = destLanguage;
        this.serviceType = servicetype;
    }
    
}
