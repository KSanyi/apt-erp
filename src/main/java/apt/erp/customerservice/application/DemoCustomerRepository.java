package apt.erp.customerservice.application;

import java.util.LinkedList;
import java.util.List;

import apt.erp.customerservice.domain.Customer;
import apt.erp.customerservice.domain.CustomerData;
import apt.erp.customerservice.domain.CustomerId;
import apt.erp.customerservice.domain.CustomerRepository;

public class DemoCustomerRepository implements CustomerRepository {

	private final List<Customer> customers;
	
	private final DemoCustomerFactory testCustomerFactory = new DemoCustomerFactory(); 
	
	public DemoCustomerRepository(int numberOfCustomers) {
		customers = generateRandomCustomers(numberOfCustomers);
	}
	
	public List<Customer> loadAllCustomers() {
		return customers;
	}

	public void saveCustomer(Customer customer) {
		customers.add(customer);
	}

	public void updateCustomer(CustomerId customerId, CustomerData customerData) {
		deleteCustomer(customerId);
		saveCustomer(new Customer(customerId, customerData));
	}

	public void deleteCustomer(CustomerId customerId) {
		customers.stream().filter(c -> c.customerId.equals(customerId)).findAny().ifPresent(c -> customers.remove(c));
	}
	
	private List<Customer> generateRandomCustomers(int n) {
		List<Customer> customers = new LinkedList<Customer>();
		for(int i=0;i<n;i++) {
			customers.add(testCustomerFactory.createRandomCustomer());
		}
		return customers;
	}

	@Override
	public boolean doesCustomerIdExist(CustomerId customerId) {
		return customers.stream().filter(c -> c.customerId.equals(customerId)).findAny().isPresent();
	}
	
}
