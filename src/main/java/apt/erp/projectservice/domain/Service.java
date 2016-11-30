package apt.erp.projectservice.domain;

public class Service {

    public final ServiceType servicetype;
    
    public final Language sourceLanguage;
    
    public final Language destLanguage;

    public Service(ServiceType servicetype, Language sourceLanguage, Language destLanguage) {
        this.servicetype = servicetype;
        this.sourceLanguage = sourceLanguage;
        this.destLanguage = destLanguage;
    }
    
}
