package apt.erp.customerservice.ui.customerdatawindow;

import apt.erp.customerservice.domain.CustomerData;

@FunctionalInterface
public interface CustomerDataChangeListener {
	
	void notifyCustomerDataChanged(CustomerData customerData);
	
}
