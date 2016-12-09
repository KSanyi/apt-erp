package apt.erp.translatorservice.application;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apt.erp.translatorservice.domain.Translator;
import apt.erp.translatorservice.domain.TranslatorId;
import apt.erp.translatorservice.domain.TranslatorRepository;

public class DemoTranslatorRepository implements TranslatorRepository {

    private static final Logger logger = LoggerFactory.getLogger(DemoTranslatorRepository.class);
    
	private final List<Translator> translators;
	
	private final DemoTranslatorFactory demoTranslatorFactory = new DemoTranslatorFactory(); 
	
	public DemoTranslatorRepository(int numberOfTranslators) {
	    translators = generateRandomTranslators(numberOfTranslators);
		logger.debug("Translator created: " + translators.stream().map(Translator::toDetailedString).collect(Collectors.joining("\n")));
	}
	
	private List<Translator> generateRandomTranslators(int n) {
		List<Translator> translators = new LinkedList<>();
		for(int i=0;i<n;i++) {
		    translators.add(demoTranslatorFactory.createRandomTranslator());
		}
		return translators;
	}

	@Override
	public String toString() {
		return "DemoTranslatorRepository with " + translators.size() + " translators";
	}

    @Override
    public List<Translator> loadAllTranslators() {
        return translators;
    }

    @Override
    public void saveTranslator(TranslatorId translatorId, Translator translator) {
        Translator newTranslator = new Translator(translatorId, translator.contactData, translator.invoicingData, translator.languages(),
                translator.services(), translator.domains(), translator.comment);
        translators.add(newTranslator);
    }

    @Override
    public void updateTranslator(Translator translator) {
        deleteTranslator(translator.id);
        saveTranslator(translator.id, translator);
    }

    @Override
    public void deleteTranslator(TranslatorId translatorId) {
        translators.stream().filter(t -> t.id.equals(translatorId)).findAny().ifPresent(c -> translators.remove(c));
    }

    @Override
    public boolean doesTranslatorIdExist(TranslatorId translatorId) {
        return translators.stream().filter(t -> t.id.equals(translatorId)).findAny().isPresent();
    }
	
}
