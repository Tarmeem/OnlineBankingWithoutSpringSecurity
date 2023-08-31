package com.onlinebankingnew.onlinebankingnew.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinebankingnew.onlinebankingnew.entity.Transaction;


public interface TransactionRepo extends JpaRepository<Transaction, Long> {

	public List<Transaction> findByAccountAccountId(Long accountId) ;
}
