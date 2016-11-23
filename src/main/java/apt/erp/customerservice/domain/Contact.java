package apt.erp.customerservice.domain;

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
    
}
