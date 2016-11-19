package apt.erp.common.vaadin;

import com.google.gwt.thirdparty.guava.common.base.Predicate;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;

@SuppressWarnings("serial")
public class TableFilter<T> implements Filter {

	private final Predicate<T> filterPredicate;
	
	public TableFilter(Predicate<T> filterPredicate){
		this.filterPredicate = filterPredicate;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean passesFilter(Object itemId, Item item) throws UnsupportedOperationException {
		return filterPredicate.apply((T)itemId);
	}

	@Override
	public boolean appliesToProperty(Object propertyId) {
		return false;
	}

}
