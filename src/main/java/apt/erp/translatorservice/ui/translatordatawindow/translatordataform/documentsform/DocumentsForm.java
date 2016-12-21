package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.documentsform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.ui.HorizontalLayout;

import apt.erp.translatorservice.domain.Document;
import apt.erp.translatorservice.ui.translatordatawindow.translatordataform.DocumentsTable;

@SuppressWarnings("serial")
public class DocumentsForm extends HorizontalLayout {

    private final List<DocumentsTable> docTables;
    
    public DocumentsForm(List<Document> documents) {
        docTables = createDocTables(documents);
        createLayout();
    }
    
    private void createLayout() {
        setSpacing(true);
        setMargin(true);
        
        docTables.stream().forEach(this::addComponent);
    }
    
    private static List<DocumentsTable> createDocTables(List<Document> docs) {
        List<DocumentsTable> docTables = new ArrayList<>();
        Collections.sort(docs, Comparator.comparing((Document doc )-> doc.uploadDate).reversed());
        Stream.of(Document.Type.values()).forEachOrdered(docType -> docTables.add(new DocumentsTable(filterDocs(docs, docType), docType)));
        return docTables;
    }
    
    private static List<Document> filterDocs(List<Document> docs, Document.Type docType) {
       return docs.stream().filter(doc -> doc.type == docType).collect(Collectors.toList()); 
    }
    
    public List<Document> getDocuments() {
        return Collections.emptyList();
    }
    
}
