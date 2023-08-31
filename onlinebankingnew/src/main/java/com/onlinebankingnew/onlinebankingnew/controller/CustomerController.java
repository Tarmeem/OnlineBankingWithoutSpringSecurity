package com.onlinebankingnew.onlinebankingnew.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.onlinebankingnew.onlinebankingnew.entity.Customer;
import com.onlinebankingnew.onlinebankingnew.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService custService;
	
	@PostMapping("/addCustomer")
	public Customer addCustomer(@Valid @RequestBody Customer c) {
		return custService.addCustomer(c);
	}
	
//	@PostMapping("/update/{id}")
//	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer c2) {
//		Customer updated = custService.update(id, c2);
//
//        if (updated != null) {
//            return ResponseEntity.ok(updated);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//	}
	@PostMapping("/update/{id}")
	public Customer updateCustomer( @PathVariable Long id,@Valid @RequestBody Customer c) {
		return custService.update(id, c);
	}
	
	
}
