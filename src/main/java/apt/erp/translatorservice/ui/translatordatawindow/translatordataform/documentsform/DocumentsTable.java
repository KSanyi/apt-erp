package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.documentsform;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.translatorservice.domain.Document;

@SuppressWarnings("serial")
class DocumentsTable extends VerticalLayout {

	private final List<Document> documents;
	
    private final Grid<Document> table;
    
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
    
    public DocumentsTable(List<Document> documents, Document.Type docType) {
        
    	this.documents = documents;
        table = createTable(docType.toString());
        documents.stream().forEach(this::addDocument);
        
        createLayout();
    }
    
    private void createLayout() {
    	setSpacing(true);
    	
        table.addStyleName(ValoTheme.TABLE_SMALL);
        table.addStyleName(ValoTheme.TABLE_NO_STRIPES);
        table.setWidth("310px");
        
        addComponent(table);
    }
    
    private Grid<Document> createTable(String caption) {
    	Grid<Document> table = new Grid<>(caption);
    	
    	table.addColumn(d -> d.name).setCaption("Név").setWidth(150);
    	table.addColumn(d -> dateTimeFormatter.format(d.uploadDate)).setCaption("Feltöltve").setWidth(110);
    	table.addColumn(d -> d.name).setCaption("");
    	
    	table.addColumn(d -> "Delete", new ButtonRenderer<Document>(e -> {
			documents.remove(e.getItem());
			table.setItems(documents);
		}));
    	
        return table;
    }
    
    void addDocument(Document doc) {
    	documents.add(doc);
    	table.setItems(documents);
    }
    
	List<Document> getDocuments() {
        return documents;
    }

}
