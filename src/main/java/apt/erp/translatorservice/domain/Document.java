package apt.erp.translatorservice.domain;

import java.time.LocalDateTime;

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
        CV("CV"), DataForm("Adatlap"), ConfidentialityDeclaration("Titoktartási nyilatkozat"), LoyaltyDeclaration("Hűségnyilatkozat"), Other("Egyedi nyilatkozat");
        
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
