package apt.erp.translatorservice.ui.translatordatawindow.translatordataform;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.translatorservice.domain.Document;

@SuppressWarnings("serial")
public class DocumentsTable extends VerticalLayout {

    private final Table table;
    
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm");
    
    public DocumentsTable(List<Document> documents, Document.Type docType) {
        
        table = createTable(docType.toString());
        for(Document doc : documents) {
            table.addItem(new Object[]{doc.name, dateTimeFormatter.format(doc.uploadDate)}, doc);
        }
        
        createLayout();
    }

    private void createLayout() {
        table.addStyleName(ValoTheme.TABLE_SMALL);
        table.addStyleName(ValoTheme.TABLE_NO_STRIPES);
        table.setPageLength(3);
        table.setWidth("225px");
        table.setColumnWidth("name", 110);
        table.setColumnWidth("date", 110);
        
        addComponent(table);
    }
    
    private static Table createTable(String caption) {
        Table table = new Table(caption);
        table.addContainerProperty("name", String.class, null);
        table.addContainerProperty("date", String.class, null);

        table.setColumnHeaders("Név", "Feltöltve");
        table.setSelectable(true);
        table.setSortEnabled(false);
        return table;
    }
    
}
