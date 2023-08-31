package com.onlinebankingnew.onlinebankingnew.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinebankingnew.onlinebankingnew.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
   public Customer findByCustomerNameAndCustomerPassword(String customerName, String customerPassword);

public boolean existsByCustomerName(String name);

public boolean existsByCustomerEmail(String email);

public boolean existsByPhoneNo(String phoneNo);

public Customer findByCustomerName(String username);

public Customer findByCustomerEmailAndCustomerPassword(String email, String password);


}
