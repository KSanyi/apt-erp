package apt.erp.customerservice.web.customerform;

import apt.erp.customerservice.domain.CustomerData;

@FunctionalInterface
public interface CustomerDataChangeListener {
	
	void notifyCustomerDataChanged(CustomerData customerData);
	
}
