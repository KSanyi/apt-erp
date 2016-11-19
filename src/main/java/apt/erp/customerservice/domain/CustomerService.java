package apt.erp.customerservice.domain;

import java.util.List;

import apt.erp.common.IdGenerator;

public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	public CustomerId createCustomer(CustomerData customerData){
		validateCustomerData(customerData);
		CustomerId customerId = generateCustomerId();
		Customer customer = new Customer(customerId, customerData);
		customerRepository.saveCustomer(customer);
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
		customerRepository.deleteCustomer(customerId);
	}
	
	public void updateCustomerData(Customer customer, CustomerData updatedCustomerData) {
		customerRepository.updateCustomer(customer.customerId, updatedCustomerData);
	}
	
	public List<Customer> loadAllCustomers() {
		return customerRepository.loadAllCustomers();
	}
	
}
