package com.onlinebankingnew.onlinebankingnew.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebankingnew.onlinebankingnew.entity.Account;
import com.onlinebankingnew.onlinebankingnew.entity.Customer;
import com.onlinebankingnew.onlinebankingnew.entity.Transaction;
import com.onlinebankingnew.onlinebankingnew.entity.Transactiontype;
import com.onlinebankingnew.onlinebankingnew.exception.InsufficientFundsException;
import com.onlinebankingnew.onlinebankingnew.exception.NegativeValueException;
import com.onlinebankingnew.onlinebankingnew.exception.ResourceNotFoundException;
import com.onlinebankingnew.onlinebankingnew.repository.AccountRepo;
import com.onlinebankingnew.onlinebankingnew.repository.CustomerRepo;

@Service
public class AccountService {

	@Autowired
	private AccountRepo accountRepo;

	@Autowired
	private TransactionService transactionservice;

	@Autowired
	private CustomerRepo customerRepo;

	public Account addAccountDetails(Account acc) {
		return accountRepo.save(acc);
	}

	public List<Account> getAllAccountById(Long id) {
		Optional<Customer> cust = customerRepo.findById(id);
		if (cust.isPresent())
			return accountRepo.findByCustomerCustomerId(id);
		else
			throw new ResourceNotFoundException("Customer id does not exists.");
	}

	public void deleteById(Long id) {
		accountRepo.deleteById(id);
	}

	public Account getAccountById(Long id) {
		return accountRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found with id  " + id));
	}

	public Double getAccountBalanceByAccountId(Long id) {
		Optional<Account> acc = accountRepo.findById(id);
		if (acc.isPresent()) {
			Account acc1 = acc.get();
			return acc1.getAccountBalance();
		} else
			throw new ResourceNotFoundException("Account does not exists.");
	}

	public void addMoneytoAccount(Long i, double amt) {
		Account acc = accountRepo.findById(i).get();
		if (amt < 0) {
			throw new NegativeValueException("Value must be grater than zero");
		}
		else {
			Double d = acc.getAccountBalance() + amt;
			acc.setAccountBalance(d);

			accountRepo.save(acc);

			Transaction t = new Transaction();
			t.setAccount(acc);
			t.setAmount(amt);
			t.setType(Transactiontype.DEPOSIT);
			t.setTimestamp(LocalDateTime.now());
			transactionservice.saveTransaction(t);
		}
	}

	public void withdrawMoneyfromAccount(Long i, double amt) {
		Account acc = accountRepo.findById(i).get();
		if (acc.getAccountBalance() < amt) {
			throw new InsufficientFundsException("Insufficient Balanace ");
		} else {
			Double d = acc.getAccountBalance() - amt;
			acc.setAccountBalance(d);

			accountRepo.save(acc);

			Transaction t = new Transaction();
			t.setAccount(acc);
			t.setAmount(amt);
			t.setType(Transactiontype.WITHDRAW);
			t.setTimestamp(LocalDateTime.now());
			transactionservice.saveTransaction(t);
		}
	}

	public void TransferMoney(Long sid, Long tid, double amt) {
		Account acc = accountRepo.findById(sid).get();
		if (acc.getAccountBalance() < amt) {
			throw new InsufficientFundsException("Insufficient Balanace ");
		}
		if(sid.equals(tid))
		{	
			throw new IllegalArgumentException("Transfer failed: Source and target accounts cannot be the same.");
		}
			Account acc2 = accountRepo.findById(tid).orElseThrow(()->new ResourceNotFoundException("Target account nor found"));
		
			Double a = acc.getAccountBalance() - amt;

			Double d = acc2.getAccountBalance() + amt;
			acc.setAccountBalance(a);

			acc2.setAccountBalance(d);

			accountRepo.save(acc);

			accountRepo.save(acc2);

			Transaction t = new Transaction();
			t.setAccount(acc);
			t.setAmount(amt);
			t.setType(Transactiontype.WITHDRAW);
			t.setTimestamp(LocalDateTime.now());
			transactionservice.saveTransaction(t);

			Transaction t1 = new Transaction();
			t1.setAccount(acc2);
			t1.setAmount(amt);
			t1.setType(Transactiontype.DEPOSIT);
			t1.setTimestamp(LocalDateTime.now());
			transactionservice.saveTransaction(t1);
		
	}
	public List<Account> getAccountsByCustomer(Customer customer) {
        return accountRepo.findByCustomer(customer);
    }

    public Account getCustomerByUsername(String username) {
        return null;
    }

	public Account createAccount(Account account) {
		return accountRepo.save(account);
	}

	public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

	public void transferMoney(Long sourceAccountId, Long targetAccountId, Double amount) {
		Account sourceAccount = accountRepo.findById(sourceAccountId)
				.orElseThrow(() -> new ResourceNotFoundException("Source account not found"));
	
		Account targetAccount = accountRepo.findById(targetAccountId)
				.orElseThrow(() -> new ResourceNotFoundException("Target account not found"));
	
		if (sourceAccount.getAccountBalance() < amount) {
			throw new InsufficientFundsException("Insufficient balance in source account");
		}
	
		Double newSourceBalance = sourceAccount.getAccountBalance() - amount;
		sourceAccount.setAccountBalance(newSourceBalance);
		accountRepo.save(sourceAccount);
	
		Double newTargetBalance = targetAccount.getAccountBalance() + amount;
		targetAccount.setAccountBalance(newTargetBalance);
		accountRepo.save(targetAccount);
	
		// Create and save transaction records for both accounts
		Transaction sourceTransaction = new Transaction();
		sourceTransaction.setAccount(sourceAccount);
		sourceTransaction.setAmount(-amount); // Negative amount for withdrawal
		sourceTransaction.setType(Transactiontype.WITHDRAW);
		sourceTransaction.setTimestamp(LocalDateTime.now());
		transactionservice.saveTransaction(sourceTransaction);
	
		Transaction targetTransaction = new Transaction();
		targetTransaction.setAccount(targetAccount);
		targetTransaction.setAmount(amount); // Positive amount for deposit
		targetTransaction.setType(Transactiontype.DEPOSIT);
		targetTransaction.setTimestamp(LocalDateTime.now());
		transactionservice.saveTransaction(targetTransaction);
	}
	

}
