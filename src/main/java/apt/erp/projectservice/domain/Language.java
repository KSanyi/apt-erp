package apt.erp.projectservice.domain;

public enum Language  {

    English("Angol"),
    German("Német"),
    French("Francia"),
    Italian("Olasz"),
    Spanish("Spanyol"),
    Portugese("Portugál"),
    Norvegian("Norvég"),
    Swedish("Svéd"),
    Danish("Dán"),
    Finnish("Finn"),
    Russian("Orosz"),
    Hungarian("Magyar"),
    Chinese("Kínai"),
    Arabian("Arab"),
    Polish("Lengyel"),
    Slovakian("Szolvák"),
    Czech("Cseh"),
    Ukranian("Ukrán"),
    Bulgarian("Bolgár"),
    Hindi("Hindu"),
    Turkish("Török"),
    Japanese("Japán");
    
	private final String caption;
	
    private Language(String caption) {
		this.caption = caption;
	}

	@Override
    public String toString() {
        return caption;
    }
    
}
