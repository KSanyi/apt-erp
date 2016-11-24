package apt.erp.customerservice.domain;

import apt.erp.common.SimpleValueObject;

public class TaxId extends SimpleValueObject {

    public static final TaxId Unknown = new TaxId("Unknown");
    
    public TaxId(String value) {
        super(value);
    }
    
    @Override
    public boolean equals(Object other) {
        return other instanceof Name ? ((TaxId)other).value.equals(value) : false;
    }

}
