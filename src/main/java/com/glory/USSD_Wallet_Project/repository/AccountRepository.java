package com.glory.USSD_Wallet_Project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.glory.USSD_Wallet_Project.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    Optional<Account> findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);



}

