package com.glory.USSD_Wallet_Project.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.glory.USSD_Wallet_Project.dto.TransactionDto;
import com.glory.USSD_Wallet_Project.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")

public class TransactionController {

    private final TransactionService transactionService;


    @GetMapping("/history")
    public ResponseEntity<List<TransactionDto.Response>> getHistory(
            @RequestParam String phoneNumber) {

        return ResponseEntity.ok(transactionService.getTransactionHistory(phoneNumber));
    }
}

