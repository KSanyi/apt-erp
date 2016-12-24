package apt.erp.translatorservice.domain;

public class Availability {
    
    public final boolean isMainJob;
    
    public final DailyAvailability dailyAvailability;
    
    public Availability(boolean isMainJob, DailyAvailability dailyAvailability) {
        this.isMainJob = isMainJob;
        this.dailyAvailability = dailyAvailability;
    }

    public static enum DailyAvailability {
        
        Always("Mindig"), Weekends("Hétvégén"), AfterWork("Munkaidőn kívül"), InWorkingHours("Hétköznap napközben");
        
        private String caption;
        
        DailyAvailability(String caption) {
            this.caption = caption;
        }
        
        @Override
        public String toString() {
            return caption;
        }
        
    }
    
}
