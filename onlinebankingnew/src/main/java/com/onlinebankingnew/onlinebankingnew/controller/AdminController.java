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

import com.onlinebankingnew.onlinebankingnew.entity.Customer;
import com.onlinebankingnew.onlinebankingnew.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/getById/{id}")
	public Customer getById(@PathVariable Long id)
	{
		return adminService.getById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteById(@PathVariable Long id)
	{
		adminService.deleteById(id);
		return "deleted";
	}
	@GetMapping("/getAllcustomers")
	public List<Customer> getAll(){
		return adminService.getAllCustomer();
	}
	
	@PostMapping("/update/{id}")
	public Customer updateCustomer(@PathVariable Long id,@Valid @RequestBody Customer c) {
		return adminService.updateById(id, c);
	}
}
