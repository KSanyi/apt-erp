package apt.erp.translatorservice.ui;

import java.util.Set;
import java.util.stream.Collectors;

import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;

import apt.erp.common.vaadin.KitsTheme;
import apt.erp.common.vaadin.ZipTownMap;
import apt.erp.projectservice.domain.Language;
import apt.erp.translatorservice.domain.Translator;
import apt.erp.translatorservice.domain.TranslatorService;
import apt.erp.translatorservice.ui.translatordatawindow.UpdateTranslatorDataWindow;

@SuppressWarnings("serial")
class TranslatorsTable extends Grid<Translator> {

	private final TranslatorService translatorService;
	
	public TranslatorsTable(TranslatorService translatorService, ZipTownMap zipTownMap) {
	    this.translatorService = translatorService;
		
	    addColumn(t -> t.personalData.name).setCaption("Név").setId("name");
	    addColumn(t -> t.languages().stream().map(Language::toString).collect(Collectors.joining(", "))).setCaption("Nyelvek");
	    
	    sort("name");
	    
		refresh();

		addStyleName(KitsTheme.GRID_SMALL);
		setSizeFull();

		addItemClickListener(event -> {
			Translator translator = event.getItem();
			UpdateTranslatorDataWindow updateTranslatorWindow = new UpdateTranslatorDataWindow(translatorService, translator, zipTownMap);
			updateTranslatorWindow.addTranslatorDataChangeListener(c -> refresh());
			UI.getCurrent().addWindow(updateTranslatorWindow);
		});
	}
	
	public void refresh() {
		Set<Translator> selectedItems = getSelectedItems();
		
		setItems(translatorService.loadAllTranslators());
		
		if(!selectedItems.isEmpty()) {
			select(selectedItems.iterator().next());
		}
	}
	
	/*
	
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
	
	*/

}
