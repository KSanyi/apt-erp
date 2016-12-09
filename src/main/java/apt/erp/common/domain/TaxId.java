package apt.erp.common.domain;

public class TaxId extends SimpleValueObject {

    public static final TaxId Unknown = new TaxId("");
    
    public TaxId(String value) {
        super(value);
    }
    
    @Override
    public boolean equals(Object other) {
        return other instanceof TaxId ? ((TaxId)other).value.equals(value) : false;
    }

}
