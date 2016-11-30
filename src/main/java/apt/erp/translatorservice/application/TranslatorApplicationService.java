package apt.erp.translatorservice.application;

import apt.erp.translatorservice.domain.TranslatorRepository;
import apt.erp.translatorservice.domain.TranslatorService;
import apt.erp.translatorservice.ui.TranslatorsWindow;

public class TranslatorApplicationService {

	private final TranslatorService translatorService;
	
	public TranslatorsWindow getTranslatorsWindow() {
		return new TranslatorsWindow(translatorService);
	}
	
	public TranslatorApplicationService(TranslatorRepository translatorRepository) {
	    translatorService = new TranslatorService(translatorRepository);
	}
	
}
