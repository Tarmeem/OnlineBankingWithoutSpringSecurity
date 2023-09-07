package com.onlinebankingnew.onlinebankingnew.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.onlinebankingnew.onlinebankingnew.entity.Account;
import com.onlinebankingnew.onlinebankingnew.entity.Transaction;
import com.onlinebankingnew.onlinebankingnew.entity.Transactiontype;
import com.onlinebankingnew.onlinebankingnew.exception.ResourceNotFoundException;
import com.onlinebankingnew.onlinebankingnew.repository.AccountRepo;
import com.onlinebankingnew.onlinebankingnew.repository.TransactionRepo;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepo transactionrepo;
	
	@Autowired
	private AccountRepo accountRepo;
	
	public Transaction saveTransaction(Transaction t) {
		return transactionrepo.save(t);
	}
	
	//  get all transactions by id for a account
	public List<Transaction> getAllTransactions( Long Ai){
		Optional<Account> account = accountRepo.findById(Ai);
		if(account.isPresent())
			return transactionrepo.findByAccountAccountId(Ai);
		else
			throw new ResourceNotFoundException("Id does not exists.");
	}
	
	// get transaction By trns id
	public Transaction getTransactionByTid(Long tid)
	{
		return transactionrepo.findById(tid).orElseThrow(()->new ResourceNotFoundException("Id does not exists."));
	}
	
	// get Total transaction amount
	public Double getTotalTransactionAmount(Long aid) {
		List<Transaction> t = transactionrepo.findByAccountAccountId(aid);
		
		if(t.isEmpty())
			throw new ResourceNotFoundException("Id does not exists.");
		
		Double d = 0.0;
		for(Transaction t1 : t)
		{
			if(t1.getType().equals(Transactiontype.DEPOSIT))
				d+=t1.getAmount();
			else if (t1.getType().equals(Transactiontype.WITHDRAW))
				d-=t1.getAmount();
		}
		return d;
	}

	public List<Transaction> getTransactionsByCustomerId(Long customerId) {
		return transactionrepo.findByAccount_Customer_CustomerIdOrderByTimestampDesc(customerId);
	}
	 public Page<Transaction> getTransactionsByCustomerId(Long customerId, Pageable pageable) {
        return transactionrepo.findByAccount_Customer_CustomerIdOrderByTimestampDesc(customerId, pageable);
    }

}