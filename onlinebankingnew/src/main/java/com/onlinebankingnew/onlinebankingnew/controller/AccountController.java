package com.onlinebankingnew.onlinebankingnew.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onlinebankingnew.onlinebankingnew.entity.Account;
import com.onlinebankingnew.onlinebankingnew.entity.Customer;
import com.onlinebankingnew.onlinebankingnew.service.AccountService;
import com.onlinebankingnew.onlinebankingnew.service.CustomerService;

@Controller
@RequestMapping("/customer/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/add/{id}")
	public Account addAccountDetails(@Valid @RequestBody Account a, @PathVariable Long id ) {
		Customer cust = customerService.getById(id);
		a.setCustomer(cust);
		return accountService.addAccountDetails(a);
	}

	
	// Get all accounts of a customer by customer Id
	@GetMapping("/getAllAccounts/{id}")
	public List<Account> getAllDetails(@PathVariable Long id){
		return accountService.getAllAccountById(id);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public String deleteById(@PathVariable Long id)
	{
		accountService.deleteById(id);
		return "Account deleted";
	}
	
	// Get account by account Id
	@GetMapping("/getbyId/{i}")
	public Account getAccountById(@PathVariable Long i) {
		return accountService.getAccountById(i);
	}
	
	@GetMapping("/{id}")
	public Customer getCustomerByAccountId(@PathVariable Long id) {
		
		Account acc = accountService.getAccountById(id);
		Long i1 = acc.getCustomer().getCustomerId();
		Customer c = customerService.getById(i1);
		return c;
		
	}
	
	@GetMapping("/balance/{id}")
	public Double getAccountBalance(@PathVariable Long id) {
		return accountService.getAccountBalanceByAccountId(id);
	}
	
	@PostMapping("/add-money/{id}")
	public String addMoney(@PathVariable Long id,@Valid @RequestBody Double amt) {
		 accountService.addMoneytoAccount(id, amt);
		return "Money Deposited Successfully";
	}
	
	@PostMapping("/withdraw-money/{id}")
	public String withdrawMoney(@PathVariable Long id,@Valid @RequestBody Double amt) {
		 accountService.withdrawMoneyfromAccount(id, amt);
		return "Money Withdrawn Successfully";
	}
	
	@PostMapping("/send-money/{sid}/{tid}")
	public String TransferMOney(@PathVariable Long sid, @PathVariable Long tid,@Valid @RequestBody Double d) {
		accountService.TransferMoney(sid,tid,d);
		return "Transfer Successfull";
	}
}
