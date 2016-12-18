package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.languageskillsform;

import java.util.ArrayList;
import java.util.List;

import apt.erp.projectservice.domain.Language;
import apt.erp.projectservice.domain.LanguageService;

class ServiceFormData {

	Language languageFrom;
	Language languageTo;
	boolean translation;
	boolean interpretation;
	boolean lectoring;
	
	ServiceFormData(Language languageFrom, Language languageTo, boolean translation, boolean interpretation, boolean lectoring) {
		this.languageFrom = languageFrom;
		this.languageTo = languageTo;
		this.translation = translation;
		this.interpretation = interpretation;
		this.lectoring = lectoring;
	}

	static ServiceFormData createEmpty() {
		return new ServiceFormData(Language.Hungarian, Language.Hungarian, false, false, false);
	}
	
	static List<ServiceFormData> createServiceFormDatas(List<LanguageService> services) {
	
		List<ServiceFormData> servicesFormDatas = new ArrayList<>();
		
		for(LanguageService service : services) {
			ServiceFormData serviceFormData = servicesFormDatas.stream()
					.filter(servicesFormData -> servicesFormData.languageFrom == service.sourceLanguage && servicesFormData.languageTo == service.destLanguage)
					.findAny().orElse(null);
			
			if(serviceFormData == null) {
				serviceFormData = new ServiceFormData(service.sourceLanguage, service.destLanguage, false, false, false);
				servicesFormDatas.add(serviceFormData);
			}
			
			switch(service.serviceType) {
				case Translation: serviceFormData.translation = true; break;
				case Lectoring: serviceFormData.lectoring = true; break;
				case Interpreting: serviceFormData.interpretation = true; break;
			}
		}
		
		return servicesFormDatas;
	}
	
}
