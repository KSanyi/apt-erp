package apt.erp.translatorservice.domain;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apt.erp.common.domain.ValidationError;
import apt.erp.common.domain.IdGenerator;

public class TranslatorService {

	private static final Logger logger = LoggerFactory.getLogger(TranslatorService.class);
	
	private final TranslatorRepository translatorRepository;

	public TranslatorService(TranslatorRepository translatorRepository) {
		this.translatorRepository = translatorRepository;
		logger.debug("Translator service initialized with " + translatorRepository);
	}
	
	public TranslatorId createTranslator(Translator translator) {
		validateTranslator(translator);
		TranslatorId translatorId = generateTranslatorId();
		translatorRepository.saveTranslator(translatorId, translator);
		logger.info("Translator created: " + translator);
		return translatorId;
	}
	
	private TranslatorId generateTranslatorId() {
		for(int attempt = 0;attempt < 100; attempt++){
			TranslatorId translatorId = IdGenerator.generateTranslatorId();
			if(!translatorRepository.doesTranslatorIdExist(translatorId)){
				logger.info("Translator id generated: " + translatorId);
				return translatorId;
			}
		}
		throw new ValidationError("Could not generate unique translator id");
	}
	
	private void validateTranslator(Translator translator) {
	}
	
	public void deleteTranslator(TranslatorId translatorId) {
	    translatorRepository.deleteTranslator(translatorId);
		logger.info("Translator deleted: " + translatorId);
	}
	
	public void updateTranslator(Translator updatedTranslator) {
		validateTranslator(updatedTranslator);
		translatorRepository.updateTranslator(updatedTranslator);
		logger.info("Translator updated for: " + updatedTranslator);
	}
	
	public List<Translator> loadAllTranslators() {
		return translatorRepository.loadAllTranslators();
	}
	
}
