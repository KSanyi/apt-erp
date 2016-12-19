package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import java.util.ArrayList;
import java.util.List;

import apt.erp.projectservice.domain.Language;
import apt.erp.projectservice.domain.LanguageService;
import apt.erp.projectservice.domain.LanguageServiceType;

class LanguageServiceFormData {

	Language languageFrom;
	Language languageTo;
	boolean translation;
	boolean interpretation;
	boolean lectoring;
	
	LanguageServiceFormData(Language languageFrom, Language languageTo, boolean translation, boolean interpretation, boolean lectoring) {
		this.languageFrom = languageFrom;
		this.languageTo = languageTo;
		this.translation = translation;
		this.interpretation = interpretation;
		this.lectoring = lectoring;
	}

	static LanguageServiceFormData createEmpty() {
		return new LanguageServiceFormData(Language.Hungarian, Language.Hungarian, false, false, false);
	}
	
	static List<LanguageServiceFormData> createServiceFormDatas(List<LanguageService> services) {
		List<LanguageServiceFormData> servicesFormDatas = new ArrayList<>();
		
		for(LanguageService service : services) {
			LanguageServiceFormData serviceFormData = servicesFormDatas.stream()
					.filter(servicesFormData -> servicesFormData.languageFrom == service.sourceLanguage && servicesFormData.languageTo == service.destLanguage)
					.findAny().orElse(null);
			
			if(serviceFormData == null) {
				serviceFormData = new LanguageServiceFormData(service.sourceLanguage, service.destLanguage, false, false, false);
				servicesFormDatas.add(serviceFormData);
			}
			
			switch(service.serviceType) {
				case Translation: serviceFormData.translation = true; break;
				case Lectoring: serviceFormData.lectoring = true; break;
				case Interpretation: serviceFormData.interpretation = true; break;
			}
		}
		
		return servicesFormDatas;
	}
	
	static List<LanguageService> createLanguageServices(List<LanguageServiceFormData> serviceFormDatas) {
		List<LanguageService> languageServices = new ArrayList<>();
		
		for(LanguageServiceFormData serviceFormData : serviceFormDatas) {
			if(serviceFormData.interpretation) {
				languageServices.add(new LanguageService(serviceFormData.languageFrom, serviceFormData.languageTo, LanguageServiceType.Interpretation));
			}
			if(serviceFormData.lectoring) {
				languageServices.add(new LanguageService(serviceFormData.languageFrom, serviceFormData.languageTo, LanguageServiceType.Lectoring));
			}
			if(serviceFormData.translation) {
				languageServices.add(new LanguageService(serviceFormData.languageFrom, serviceFormData.languageTo, LanguageServiceType.Translation));
			}
		}
		
		return languageServices;
	}
	
}
