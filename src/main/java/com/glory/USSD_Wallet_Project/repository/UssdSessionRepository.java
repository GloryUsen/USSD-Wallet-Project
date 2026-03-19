package com.glory.USSD_Wallet_Project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.glory.USSD_Wallet_Project.model.UssdSession;

@Repository
public interface UssdSessionRepository extends CrudRepository<UssdSession, String>{


}
