package apt.erp.customerservice.ui.customerdatawindow.customerdataform;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.customerservice.domain.Contact;

@SuppressWarnings("serial")
class ContactsTabSheet extends VerticalLayout implements CloseHandler {

	private final List<Contact> contacts;
	
	private final List<ContactForm> contactForms = new ArrayList<>();
	
	private final TabSheet tabSheet = new TabSheet();
	
	private final Button addTabButton = FormFieldFactory.createFormButton("Új kontakt személy", FontAwesome.PLUS, ValoTheme.BUTTON_LINK, click -> createNewContact());
	
	ContactsTabSheet(List<Contact> contacts) {
		this.contacts = contacts;
		
		tabSheet.setCloseHandler(this);
		for(int i=0;i<contacts.size();i++) {
		    addTab(contacts.get(i));
		}
		
		createLayout();
	}
	
	public List<Contact> getContacts() {
		return contactForms.stream().map(ContactForm::getContact).collect(Collectors.toList());
	}
	
	public boolean isDataModified() {
		return contactForms.stream().anyMatch(ContactForm::isDataModified) || contacts.size() != contactForms.size();
	}

	public boolean isValid() {
		return contactForms.stream().allMatch(ContactForm::isValid);
	}
	
	private void createLayout() {
		tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabSheet.addStyleName(ValoTheme.TABSHEET_COMPACT_TABBAR);
		addComponents(addTabButton, tabSheet);
	}

	private void createNewContact() {
	    Contact newContact = Contact.createEmpty();
        Tab newTab = addTab(newContact);
        tabSheet.setSelectedTab(newTab);
	}
	
	private Tab addTab(Contact contact) {
	    ContactForm contactForm = new ContactForm(contact);
	    contactForms.add(contactForm);
	    Tab tab = tabSheet.addTab(contactForm, "Kontakt " + contactForms.size());
        tab.setClosable(true);
        return tab;
	}
	
	@Override
	public void onTabClose(TabSheet tabSheet, Component tabContent) {
		contactForms.remove(tabContent);
		tabSheet.removeTab(tabSheet.getTab(tabContent));
	}
	
}
