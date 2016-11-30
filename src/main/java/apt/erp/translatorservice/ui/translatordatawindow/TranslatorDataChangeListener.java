package apt.erp.translatorservice.ui.translatordatawindow;

import apt.erp.translatorservice.domain.Translator;

@FunctionalInterface
public interface TranslatorDataChangeListener {
	
	void notifyTranslatorDataChanged(Translator translator);
	
}
