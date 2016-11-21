package apt.erp.customerservice.domain;

import apt.erp.common.SimpleValueObject;

public class Domain {

    public String code;
    
    public String name;

    public Domain(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public boolean matches(String filter) {
        return name.contains(filter);
    }
    
    @Override
    public boolean equals(Object other){
        if(this == other)
            return true;
        
        if(other == null)
            return false;
        
        if(this.getClass() != other.getClass())
            return false;

        return this.code.equals(((SimpleValueObject)other).value);
    }
    
    @Override
    public int hashCode() {
        return code.hashCode();
    }
    
}
