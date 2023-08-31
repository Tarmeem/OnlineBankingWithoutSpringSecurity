package com.onlinebankingnew.onlinebankingnew.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onlinebankingnew.onlinebankingnew.entity.Transaction;
import com.onlinebankingnew.onlinebankingnew.service.TransactionService;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService trs;
	
	@GetMapping("/account/{id}")
	public List<Transaction> getAll(@PathVariable Long id){
		return trs.getAllTransactions(id);
	}

	@GetMapping("/{id}")
	public Transaction getTransByTid(@PathVariable Long id) {
		return trs.getTransactionByTid(id);
	}
	
	@GetMapping("/amount/{id}")
	public Double getTTransAmtByTid(@PathVariable Long id) {
		return trs.getTotalTransactionAmount(id);
	}
	
	
	
}
