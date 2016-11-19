package apt.erp.customerservice.web.customerform;

import apt.erp.customerservice.domain.Customer;

@FunctionalInterface
public interface CustomerChangeListener {
	
	void notifyCustomerChanged(Customer customer);
	
}
