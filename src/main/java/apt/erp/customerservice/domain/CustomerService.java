package apt.erp.customerservice.domain;

import java.util.List;

import apt.erp.common.IdGenerator;

public class CustomerService {

	private final CustomerDataRepository customerRepository;

	public CustomerService(CustomerDataRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	public CustomerId createCustomer(CustomerData customerData){
		validateCustomerData(customerData);
		CustomerId customerId = generateCustomerId();
		customerRepository.saveCustomerData(customerId, customerData);
		return customerId;
	}
	
	private CustomerId generateCustomerId() {
		for(int attempt = 0;attempt < 100; attempt++){
			CustomerId customerId = IdGenerator.generateCustomerId();
			if(!customerRepository.doesCustomerIdExist(customerId)){
				return customerId;
			}
		}
		throw new CustomerServiceException("Could not generate unique customer id");
	}
	
	private void validateCustomerData(CustomerData customerData) {
		
	}
	
	public void deleteCustomer(CustomerId customerId) {
		customerRepository.deleteCustomerData(customerId);
	}
	
	public void updateCustomerData(CustomerData updatedCustomerData) {
		customerRepository.updateCustomerData(updatedCustomerData);
	}
	
	public List<CustomerData> loadAllCustomers() {
		return customerRepository.loadAllCustomerDatas();
	}
	
}
