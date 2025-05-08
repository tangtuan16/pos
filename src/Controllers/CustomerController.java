package Controllers;

import Models.Customer;
import Services.CustomerService;
import java.util.List;

public class CustomerController {

    private CustomerService service;

    public CustomerController() {
        service = new CustomerService();
    }

    public List<Customer> loadCustomers() {
        return service.getAllCustomers();
    }

    public void addCustomer(Customer customer) {
        service.saveCustomer(customer);
    }

    public boolean updateCustomer(Customer customer) {
        return service.updateCustomer(customer);
    }

    public boolean deleteCustomer(int customerId) {
        return service.deleteCustomer(customerId);
    }

    public List<Customer> searchCustomerByPhone(String phone) {
        return service.searchCustomerByPhone(phone);
    }
}
