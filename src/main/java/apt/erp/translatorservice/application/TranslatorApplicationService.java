package apt.erp.translatorservice.application;

import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.translatorservice.domain.TranslatorRepository;
import apt.erp.translatorservice.domain.TranslatorService;
import apt.erp.translatorservice.ui.TranslatorsWindow;

public class TranslatorApplicationService {

	private final TranslatorService translatorService;
	
	private final ZipTownMap zipTownMap;
	
	public TranslatorsWindow getTranslatorsWindow() {
		return new TranslatorsWindow(translatorService, zipTownMap);
	}
	
	public TranslatorApplicationService(TranslatorRepository translatorRepository, ZipTownMap zipTownMap) {
	    translatorService = new TranslatorService(translatorRepository);
	    this.zipTownMap = zipTownMap;
	}
	
}
