package com.onlinebankingnew.onlinebankingnew.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebankingnew.onlinebankingnew.entity.Customer;
import com.onlinebankingnew.onlinebankingnew.repository.AccountRepo;
import com.onlinebankingnew.onlinebankingnew.repository.CustomerRepo;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private AccountRepo accountRepo;

	public Customer addCustomer(Customer cust) {
		return customerRepo.save(cust);
				//.orElseThrow(() -> new CustomerException("Customer not found with id  "+ Id));
	}
	
	public Customer update(Long id,Customer c) {
		
            Customer customer = customerRepo.findById(id).get();
            
            customer.setCustomerName(c.getCustomerName());
            customer.setPhoneNo(c.getPhoneNo());
            customer.setCustomerEmail(c.getCustomerEmail());
            customer.setCustomerPassword(c.getCustomerPassword());
            customer.setCustomerDOB(c.getCustomerDOB());
            customer.setCustomerAddress(c.getCustomerAddress());

            return customerRepo.save(customer);
        
	}
	public Customer getById(Long id) {
		return customerRepo.findById(id).get();
	}

	public Customer getCustomerByUsernameAndPassword(String username, String password) {
		return customerRepo.findByCustomerNameAndCustomerPassword(username, password);
	}

	public boolean doesNameExist(String name) {
        return customerRepo.existsByCustomerName(name);
    }

	public boolean doesEmailExist(String email){
		return customerRepo.existsByCustomerEmail(email);
	}
	
	public boolean doesPhoneNumberExist(String phoneNo){
		return customerRepo.existsByPhoneNo(phoneNo);
	}

	public Customer getCustomerByUsername(String username) {
        return customerRepo.findByCustomerName(username);
    }

	
	// public Customer authenticateAndGetCustomer(String username, String password) {
	// 	// This is a simplified example; you should implement your actual authentication logic here
	// 	// For illustration purposes, assuming you have a hardcoded user
	// 	if (username.equals("user") && password.equals("password")) {
	// 		Customer customer = new Customer();
	// 		customer.setCustomerId(null); // Set the customer ID
	// 		// Set other customer details
	// 		return customer;
	// 	} else {
	// 		return null; // Return null for failed authentication
	// 	}
	// }

	
    public Customer authenticateAndGetCustomer(String email, String password) {
        return customerRepo.findByCustomerEmailAndCustomerPassword(email, password);
    }
	
}
