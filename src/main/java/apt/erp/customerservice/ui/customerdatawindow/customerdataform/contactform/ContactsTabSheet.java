package apt.erp.customerservice.ui.customerdatawindow.customerdataform.contactform;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import apt.erp.common.vaadin.FormFieldFactory;
import apt.erp.customerservice.domain.Contact;

@SuppressWarnings("serial")
public class ContactsTabSheet extends CustomField<List<Contact>> implements CloseHandler {

	private final List<ContactForm> contactForms = new ArrayList<>();
	
	private final TabSheet tabSheet = new TabSheet();
	
	private final Button addTabButton = FormFieldFactory.createFormButton("Új kontakt személy", VaadinIcons.PLUS, ValoTheme.BUTTON_LINK, click -> createNewContact());
	
	public ContactsTabSheet(List<Contact> contacts) {
		tabSheet.setCloseHandler(this);
		for(int i=0;i<contacts.size();i++) {
		    addTab(contacts.get(i));
		}
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

	@Override
	public List<Contact> getValue() {
		return contactForms.stream().map(ContactForm::getValue).collect(Collectors.toList());
	}

	@Override
	protected Component initContent() {
		VerticalLayout layout = new VerticalLayout(addTabButton, tabSheet);
		layout.setMargin(false);
		tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabSheet.addStyleName(ValoTheme.TABSHEET_COMPACT_TABBAR);
		return layout;
	}

	@Override
	protected void doSetValue(List<Contact> value) {
		throw new UnsupportedOperationException();
	}
	
}
