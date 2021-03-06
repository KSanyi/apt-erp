package apt.erp.translatorservice.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Document {

    public Type type;
    
    public String name;
    
    public LocalDateTime uploadDate;
    
    public Document(Type type, String name, LocalDateTime uploadDate) {
        this.type = type;
        this.name = name;
        this.uploadDate = uploadDate;
    }

    public static enum Type {
        CV("Önéletrajz"), DataForm("Adatlap"), ConfidentialityDeclaration("Titoktartási nyilatkozat"),
        LoyaltyDeclaration("Hűségnyilatkozat"), Diploma("Diploma"), Other("Egyedi nyilatkozat");
        
    	public static final List<Type> all = Arrays.asList(values());
    	
        private final String caption;
        
        Type(String caption) {
            this.caption = caption;
        }
        
        @Override
        public String toString() {
            return caption;
        }
    }
    
}
