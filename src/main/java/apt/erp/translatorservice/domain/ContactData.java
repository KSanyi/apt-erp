package apt.erp.translatorservice.domain;

import java.util.Collections;
import java.util.List;

import apt.erp.common.domain.EmailAddress;
import apt.erp.common.domain.Name;
import apt.erp.common.domain.PhoneNumber;
import apt.erp.projectservice.domain.LanguageServiceType;

public class ContactData {

    public final Name name;
    
    public final PhoneNumber phoneNumber1;
    public final PhoneNumber phoneNumber2;
    
    public final EmailAddress emailAddress1;
    public final EmailAddress emailAddress2;
    
    public final List<LanguageServiceType> serviceTypes;
    
    public ContactData(Name name, PhoneNumber phoneNumber1, PhoneNumber phoneNumber2, EmailAddress emailAddress1, EmailAddress emailAddress2, List<LanguageServiceType> serviceTypes) {
        this.name = name;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.emailAddress1 = emailAddress1;
        this.emailAddress2 = emailAddress2;
        this.serviceTypes = serviceTypes;
    }

    @Override
    public String toString() {
        return "Name: " + name + " phone: " + phoneNumber1 + " " + phoneNumber2 + " email: " + emailAddress1 + " " + emailAddress2;
    }

    public static ContactData createEmpty() {
        return new ContactData(Name.createEmpty(), PhoneNumber.createEmpty(), PhoneNumber.createEmpty(), EmailAddress.createEmpty(), EmailAddress.createEmpty(), Collections.emptyList());
    }

    public boolean matches(String filter) {
        return name.matches(filter) || emailAddress1.matches(filter) || emailAddress2.matches(filter);
    }
    
}
