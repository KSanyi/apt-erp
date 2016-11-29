package apt.erp.customerservice.domain;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apt.erp.common.IdGenerator;

public class CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
	private final CustomerDataRepository customerRepository;

	public CustomerService(CustomerDataRepository customerRepository) {
		this.customerRepository = customerRepository;
		logger.debug("Customer service initialized with " + customerRepository);
	}
	
	public CustomerId createCustomer(CustomerData customerData) {
		validateCustomerData(customerData);
		CustomerId customerId = generateCustomerId();
		customerRepository.saveCustomerData(customerId, customerData);
		logger.info("Customer data created: " + customerData);
		return customerId;
	}
	
	private CustomerId generateCustomerId() {
		for(int attempt = 0;attempt < 100; attempt++){
			CustomerId customerId = IdGenerator.generateCustomerId();
			if(!customerRepository.doesCustomerIdExist(customerId)){
				logger.info("Customer id generated: " + customerId);
				return customerId;
			}
		}
		throw new CustomerServiceException("Could not generate unique customer id");
	}
	
	private void validateCustomerData(CustomerData customerData) {
		
		boolean nonUniqueTaxId = customerRepository.loadAllCustomerDatas().stream().anyMatch(c -> !c.equals(customerData) && c.taxId.equals(customerData.taxId));
		if(nonUniqueTaxId) {
			throw new CustomerServiceException("Tax Id already exists");
		}
	}
	
	public void deleteCustomer(CustomerId customerId) {
		customerRepository.deleteCustomerData(customerId);
		logger.info("Customer data deleted: " + customerId);
	}
	
	public void updateCustomerData(CustomerData updatedCustomerData) {
		validateCustomerData(updatedCustomerData);
		customerRepository.updateCustomerData(updatedCustomerData);
		logger.info("Customer data updated for: " + updatedCustomerData);
	}
	
	public List<CustomerData> loadAllCustomers() {
		return customerRepository.loadAllCustomerDatas();
	}
	
}
