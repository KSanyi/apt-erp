package apt.erp.customerservice.domain;

public class Customer {

	public static Customer createEmptyCustomer() {
		CustomerId customerId = new CustomerId("New customer");
		return new Customer(customerId, CustomerData.createEmptyCustomerData());
	}
	
	public final CustomerId customerId;
	public final CustomerData customerData;

	public Customer(CustomerId customerId, CustomerData customerData) {
		this.customerId = customerId;
		this.customerData = customerData;
	}
	
	public boolean matches(String filter) {
		return customerId.value.contains(filter) || customerData.matches(filter);
	}
	
	@Override
	public String toString(){
		return customerId + " " + customerData;
	}

}
