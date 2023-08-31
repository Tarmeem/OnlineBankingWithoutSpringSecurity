
package com.onlinebankingnew.onlinebankingnew.service;

 

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebankingnew.onlinebankingnew.entity.Customer;
import com.onlinebankingnew.onlinebankingnew.exception.ResourceNotFoundException;
import com.onlinebankingnew.onlinebankingnew.repository.CustomerRepo;

 

@Service

public class AdminService {


    @Autowired

    private CustomerRepo customerRepo;

 

    public List<Customer> getAllCustomer(){

        return customerRepo.findAll();

    }


 

    public Customer getById(Long Id) {

 

        return customerRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id  "+ Id));


    }

 

    public void deleteById(Long Id) {

        Optional<Customer> customer = customerRepo.findById(Id);

        if(customer.isPresent())

            customerRepo.deleteById(Id);

        else 

            throw new ResourceNotFoundException("Customer not found");

    }


    public Customer updateById(Long id,Customer c) {



        Customer customer = customerRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));


        customer.setCustomerName(c.getCustomerName());

        customer.setPhoneNo(c.getPhoneNo());

        customer.setCustomerEmail(c.getCustomerEmail());

        customer.setCustomerPassword(c.getCustomerPassword());

        customer.setCustomerDOB(c.getCustomerDOB());

        customer.setCustomerAddress(c.getCustomerAddress());

 

        return customerRepo.save(customer);


}


}
