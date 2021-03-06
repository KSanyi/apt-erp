package apt.erp.translatorservice.ui;

import java.util.stream.Collectors;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.projectservice.domain.Language;
import apt.erp.translatorservice.domain.Translator;
import apt.erp.translatorservice.domain.TranslatorService;
import apt.erp.translatorservice.ui.translatordatawindow.UpdateTranslatorDataWindow;

@SuppressWarnings("serial")
class TranslatorsTable extends Table {

	private final TranslatorService translatorService;
	
	public TranslatorsTable(TranslatorService translatorService, ZipTownMap zipTownMap) {
	    this.translatorService = translatorService;
		
		addContainerProperty("Name", String.class, null);
		addContainerProperty("Languages", String.class, null);

		setSortContainerPropertyId("Name");
		refresh();

		setColumnHeaders("Név", "Nyelvek");
		
		addStyleName(ValoTheme.TABLE_SMALL);
		setSizeFull();
		setSelectable(true);

		addItemClickListener(event -> {
			Translator translator = (Translator)event.getItemId();
			UpdateTranslatorDataWindow updateTranslatorWindow = new UpdateTranslatorDataWindow(translatorService, translator, zipTownMap);
			updateTranslatorWindow.addTranslatorDataChangeListener(c -> refresh());
			UI.getCurrent().addWindow(updateTranslatorWindow);
		});
	}
	
	public void refresh() {
		
		Object selectedItem = getValue();
		int currentPageFirstItemIndex = getCurrentPageFirstItemIndex();
		
	    removeAllItems();
		for(Translator translator : translatorService.loadAllTranslators()) {
			addItem(new Object[]{translator.personalData.name.toString(), translator.languages().stream().map(Language::toString).collect(Collectors.joining(", "))}, translator);
		}
		sort();
		
		setValue(selectedItem, true);
		setCurrentPageFirstItemIndex(currentPageFirstItemIndex);
	}
	
	public void filter(String filterString) {
		IndexedContainer container = (IndexedContainer)getContainerDataSource();
		container.removeAllContainerFilters();
		if(!filterString.isEmpty()) {
			container.addContainerFilter(new TranslatorFilter(filterString));
		}
	}
	
	private static class TranslatorFilter implements Filter {
		private final String filterString;
		public TranslatorFilter(String filterString) {
			this.filterString = filterString;
		}
		
		public boolean passesFilter(Object itemId, Item item) {
		    Translator translator = (Translator)itemId;
		    return translator.matches(filterString);
		}

		public boolean appliesToProperty(Object propertyId) {
			return false;
		}
	}

}
