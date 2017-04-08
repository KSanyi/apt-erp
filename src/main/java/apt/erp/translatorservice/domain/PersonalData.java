package apt.erp.translatorservice.domain;

import apt.erp.common.domain.EmailAddress;
import apt.erp.common.domain.Name;
import apt.erp.common.domain.PhoneNumber;
import apt.erp.projectservice.domain.LanguageServiceType;

public class PersonalData {

    public final Name name;
    
    public final LanguageServiceType mainServiceType;
    
    public final PhoneNumber phoneNumber1;
    public final PhoneNumber phoneNumber2;
    
    public final EmailAddress emailAddress1;
    public final EmailAddress emailAddress2;
    
    public final String skypeId;
    
    public final CommunicationChannel preferredCommunicationChannel;
    
    public final String comments;
    
    public PersonalData(Name name, LanguageServiceType mainServiceType, PhoneNumber phoneNumber1, PhoneNumber phoneNumber2, EmailAddress emailAddress1, EmailAddress emailAddress2, String skypeId,
            CommunicationChannel preferredCommunicationChannel, String comments) {
        this.name = name;
        this.mainServiceType = mainServiceType;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.emailAddress1 = emailAddress1;
        this.emailAddress2 = emailAddress2;
        this.skypeId = skypeId;
        this.preferredCommunicationChannel = preferredCommunicationChannel;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Name: " + name + " phone: " + phoneNumber1 + " " + phoneNumber2 + " email: " + emailAddress1 + " " + emailAddress2;
    }

    public static PersonalData createEmpty() {
        return new PersonalData(Name.createEmpty(), LanguageServiceType.Translation, PhoneNumber.createEmpty(), PhoneNumber.createEmpty(), EmailAddress.createEmpty(), EmailAddress.createEmpty(), "", 
                CommunicationChannel.PHONE, "");
    }

    public boolean matches(String filter) {
        return name.matches(filter) || emailAddress1.matches(filter) || emailAddress2.matches(filter) || comments.toLowerCase().contains(filter.toLowerCase());
    }
    
    public enum CommunicationChannel {
        
        PHONE("Telefon"), SMS("SMS"), SKYPE("Skype"), VIBER("Viber"), WHATSAPP("Whatsapp"), FACEBOOK("Facebook");
        
        private String caption;
        
        CommunicationChannel(String caption) {
            this.caption = caption;
        }
        
        @Override
        public String toString() {
            return caption;
        }
        
    }
    
}
