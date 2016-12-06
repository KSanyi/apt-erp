package apt.erp.customerservice.domain;

import apt.erp.common.domain.EmailAddress;
import apt.erp.common.domain.Name;
import apt.erp.common.domain.PhoneNumber;

public class Contact {

    public final Name name;
    public final PhoneNumber phoneNumber;
    public final EmailAddress emailAddress;
    
    public Contact(Name name, PhoneNumber phoneNumber, EmailAddress emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }
    
    @Override
    public String toString() {
        return "Name: " + name + " phone: " + phoneNumber + " email: " + emailAddress;
    }

    public static Contact createEmpty() {
        return new Contact(Name.createEmpty(), PhoneNumber.createEmpty(), EmailAddress.createEmpty());
    }
    
}
