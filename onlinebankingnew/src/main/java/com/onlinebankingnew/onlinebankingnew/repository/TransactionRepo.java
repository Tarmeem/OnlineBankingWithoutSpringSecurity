package com.onlinebankingnew.onlinebankingnew.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinebankingnew.onlinebankingnew.entity.Transaction;


public interface TransactionRepo extends JpaRepository<Transaction, Long> {

	public List<Transaction> findByAccountAccountId(Long accountId) ;
	
	public List<Transaction> findByAccount_Customer_CustomerIdOrderByTimestampDesc(Long customerId);

	public Page<Transaction> findByAccount_Customer_CustomerIdOrderByTimestampDesc(Long customerId,Pageable pageable);

}
