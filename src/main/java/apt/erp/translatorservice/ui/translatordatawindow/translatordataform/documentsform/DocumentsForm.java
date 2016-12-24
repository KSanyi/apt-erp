package apt.erp.translatorservice.ui.translatordatawindow.translatordataform.documentsform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.common.vaadin.LayoutFactory;
import apt.erp.translatorservice.domain.Document;

@SuppressWarnings("serial")
public class DocumentsForm extends VerticalLayout implements Receiver, SucceededListener {

    private final Map<Document.Type, DocumentsTable> docTables;
    
    private final ComboBox docTypeCombo = createDocTypeCombo();
    
    private final Upload upload = createUpload();
    
    public DocumentsForm(List<Document> documents) {
        docTables = createDocTables(documents);
        createLayout();
    }
    
    private static Map<Document.Type, DocumentsTable> createDocTables(List<Document> docs) {
    	Map<Document.Type, DocumentsTable> docTables = new LinkedHashMap<>();
        Collections.sort(docs, Comparator.comparing((Document doc )-> doc.uploadDate).reversed());
        Stream.of(Document.Type.values()).forEachOrdered(docType -> docTables.put(docType, new DocumentsTable(filterDocs(docs, docType), docType)));
        return docTables;
    }
    
    private static List<Document> filterDocs(List<Document> docs, Document.Type docType) {
       return docs.stream().filter(doc -> doc.type == docType).collect(Collectors.toList()); 
    }
    
    private static ComboBox createDocTypeCombo() {
    	ComboBox docTypeCombo = FormFieldFactory.createEnumComboBox("Dokumentum típusa", Document.Type.class);
    	docTypeCombo.setValue(Document.Type.CV);
    	return docTypeCombo;
    }
    
    private Upload createUpload() {
    	Upload upload = new Upload("", this);
    	upload.addSucceededListener(this);
    	upload.setButtonCaption("Feltöltés");
    	return upload;
    }
    
    private void createLayout() {
        setSpacing(true);
        setMargin(true);
        setDefaultComponentAlignment(Alignment.TOP_CENTER);
        docTypeCombo.setWidth("180px");
        
        GridLayout docTablesLayout = new GridLayout(3, 2);
        docTablesLayout.setSpacing(true);
        docTables.values().stream().forEach(docTablesLayout::addComponent);
        
        Panel uploadPanel = new Panel("Új dokumentum", LayoutFactory.createHorizontalLayoutWithMargin(docTypeCombo, upload));
        
        addComponents(docTablesLayout, uploadPanel);
    }
    
    public List<Document> getDocuments() {
    	return docTables.values().stream().flatMap(docTable -> docTable.getDocuments().stream()).collect(Collectors.toList());
    }

    @Override
	public void uploadSucceeded(SucceededEvent event) {
    	String fileName = event.getFilename();
    	if(!fileName.isEmpty()) {
    		Document.Type docType = (Document.Type)docTypeCombo.getValue();
        	docTables.get(docType).addDocument(new Document(docType, fileName, LocalDateTime.now()));
    		Notification.show("File " + fileName + " feltöltve");	
    	}
	}

	@Override
	public OutputStream receiveUpload(String fileName, String mimeType) {
		try {
			return new FileOutputStream(new File("c:/tmp/file"));
		} catch (FileNotFoundException ex) {
			throw new RuntimeException("File upload failed", ex);
		}
	}
    
}
