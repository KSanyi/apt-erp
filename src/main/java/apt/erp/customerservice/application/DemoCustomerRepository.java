package apt.erp.customerservice.application;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerDataRepository;
import apt.erp.customerservice.domain.CustomerId;

public class DemoCustomerRepository implements CustomerDataRepository {

    private static final Logger logger = LoggerFactory.getLogger(DemoCustomerRepository.class);
    
	private final List<CustomerData> customerDatas;
	
	private final DemoCustomerFactory demoCustomerDataFactory = new DemoCustomerFactory(); 
	
	public DemoCustomerRepository(int numberOfCustomers) {
	    customerDatas = generateRandomCustomers(numberOfCustomers);
		logger.debug("Customers created");
	}
	
	public List<CustomerData> loadAllCustomerDatas() {
		return customerDatas;
	}

	public void saveCustomerData(CustomerId customerId, CustomerData customerData) {
	    CustomerData newCustomerData = new CustomerData(customerId, customerData.taxId, customerData.name, customerData.address, customerData.invoiceAddress, 
	            customerData.comment, customerData.mainDomain, customerData.mainLanguage, customerData.contacts());
	    customerDatas.add(newCustomerData);
	}

	public void updateCustomerData(CustomerData customerData) {
		deleteCustomerData(customerData.customerId);
		saveCustomerData(customerData.customerId, customerData);
	}

	public void deleteCustomerData(CustomerId customerId) {
	    customerDatas.stream().filter(c -> c.customerId.equals(customerId)).findAny().ifPresent(c -> customerDatas.remove(c));
	}
	
	private List<CustomerData> generateRandomCustomers(int n) {
		return IntStream
				.range(1, n+1)
				.mapToObj(i -> demoCustomerDataFactory.createRandomCustomerData())
				.collect(Collectors.toList());
	}

	@Override
	public boolean doesCustomerIdExist(CustomerId customerId) {
		return customerDatas.stream().filter(c -> c.customerId.equals(customerId)).findAny().isPresent();
	}
	
	@Override
	public String toString() {
		return "DemoCustomerRepository with " + customerDatas.size() + " customers";
	}
	
}
