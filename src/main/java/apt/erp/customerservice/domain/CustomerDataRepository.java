package apt.erp.customerservice.domain;

import java.util.List;

public interface CustomerDataRepository {

	List<CustomerData> loadAllCustomerDatas();
	
	void saveCustomerData(CustomerId customerId, CustomerData customerData);
	
	void updateCustomerData(CustomerData customerData);
	
	void deleteCustomerData(CustomerId customerId);
	
	boolean doesCustomerIdExist(CustomerId customerId);
	
}
