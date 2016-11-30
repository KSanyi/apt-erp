package apt.erp.translatorservice.domain;

import java.util.List;

public interface TranslatorRepository {

	List<Translator> loadAllTranslators();
	
	void saveTranslator(TranslatorId translatorId, Translator translator);
	
	void updateTranslator(Translator translator);
	
	void deleteTranslator(TranslatorId translatorId);
	
	boolean doesTranslatorIdExist(TranslatorId translatorId);
	
}
