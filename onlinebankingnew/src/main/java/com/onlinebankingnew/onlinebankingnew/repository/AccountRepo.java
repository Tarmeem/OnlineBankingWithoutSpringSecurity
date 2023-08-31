package com.onlinebankingnew.onlinebankingnew.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinebankingnew.onlinebankingnew.entity.Account;
import com.onlinebankingnew.onlinebankingnew.entity.Customer;

public interface AccountRepo extends JpaRepository<Account, Long>{

	public List<Account> findByCustomerCustomerId(Long customerId);

	public  Account findByAccountNumber(String accountNumber);

    public List<Account> findByCustomer(Customer customer);

}
