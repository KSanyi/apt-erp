package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.documentsform;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.translatorservice.domain.Document;

@SuppressWarnings("serial")
class DocumentsTable extends VerticalLayout {

    private final Table table;
    
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
    
    public DocumentsTable(List<Document> documents, Document.Type docType) {
        
        table = createTable(docType.toString());
        documents.stream().forEach(this::addDocument);
        
        createLayout();
    }
    
    private Button createDeleteButton(Document doc) {
    	Button button = new Button("", FontAwesome.REMOVE);
    	button.addStyleName(ValoTheme.BUTTON_DANGER);
    	button.addStyleName(ValoTheme.BUTTON_TINY);
    	button.addClickListener(click -> table.removeItem(doc));
    	return button;
    }

    private void createLayout() {
    	setSpacing(true);
    	
        table.addStyleName(ValoTheme.TABLE_SMALL);
        table.addStyleName(ValoTheme.TABLE_NO_STRIPES);
        table.setPageLength(3);
        table.setWidth("310px");
        table.setColumnWidth("name", 150);
        table.setColumnWidth("date", 110);
        
        addComponent(table);
    }
    
    private static Table createTable(String caption) {
        Table table = new Table(caption);
        table.addContainerProperty("name", String.class, null);
        table.addContainerProperty("date", String.class, null);
        table.addContainerProperty("deleteButton", Button.class, null);
        table.setColumnAlignment("deleteButton", Align.CENTER);

        table.setColumnHeaders("Név", "Feltöltve", "");
        table.setSelectable(true);
        table.addValueChangeListener(event -> table.setValue(null));
        table.setSortEnabled(false);
        return table;
    }
    
    void addDocument(Document doc) {
    	table.addItem(new Object[]{doc.name, dateTimeFormatter.format(doc.uploadDate), createDeleteButton(doc) }, doc);
    }
    
    @SuppressWarnings("unchecked")
	List<Document> getDocuments() {
        return (List<Document>)table.getItemIds();
    }

}
