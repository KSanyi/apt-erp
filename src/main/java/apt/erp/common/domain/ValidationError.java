package apt.erp.common.domain;

@SuppressWarnings("serial")
public class ValidationError extends RuntimeException {
    
    public ValidationError(String message){
        super(message);
    }

}
