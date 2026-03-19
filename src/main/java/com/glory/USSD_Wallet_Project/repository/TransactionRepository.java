package com.glory.USSD_Wallet_Project.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.glory.USSD_Wallet_Project.model.Transaction;

@Repository

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    Transaction findByReference(String reference);
    List<Transaction> findByAccountAccountNumber(String accountNumber);
    List<Transaction> findByTransactionDate(LocalDateTime transactionDate);

}
