package com.glory.USSD_Wallet_Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.glory.USSD_Wallet_Project.model.Account;
import com.glory.USSD_Wallet_Project.model.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{

    BankAccount findByAccount_PhoneNumber(String phoneNumber);
    boolean existsByAccount(Account account);

}
