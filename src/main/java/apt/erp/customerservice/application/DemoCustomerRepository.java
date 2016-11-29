package apt.erp.customerservice.application;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerId;
import apt.erp.customerservice.domain.CustomerDataRepository;

public class DemoCustomerRepository implements CustomerDataRepository {

    private static final Logger logger = LoggerFactory.getLogger(DemoCustomerRepository.class);
    
	private final List<CustomerData> customerDatas;
	
	private final DemoCustomerFactory demoCustomerDataFactory = new DemoCustomerFactory(); 
	
	public DemoCustomerRepository(int numberOfCustomers) {
	    customerDatas = generateRandomCustomers(numberOfCustomers);
		logger.debug("Customers created: " + customerDatas.stream().map(CustomerData::toDetailedString).collect(Collectors.joining("\n")));
	}
	
	public List<CustomerData> loadAllCustomerDatas() {
		return customerDatas;
	}

	public void saveCustomerData(CustomerId customerId, CustomerData customerData) {
	    customerDatas.add(customerData);
	}

	public void updateCustomerData(CustomerData customerData) {
		deleteCustomerData(customerData.customerId);
		saveCustomerData(customerData.customerId, customerData);
	}

	public void deleteCustomerData(CustomerId customerId) {
	    customerDatas.stream().filter(c -> c.customerId.equals(customerId)).findAny().ifPresent(c -> customerDatas.remove(c));
	}
	
	private List<CustomerData> generateRandomCustomers(int n) {
		List<CustomerData> customerDatas = new LinkedList<CustomerData>();
		for(int i=0;i<n;i++) {
		    customerDatas.add(demoCustomerDataFactory.createRandomCustomerData());
		}
		return customerDatas;
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
