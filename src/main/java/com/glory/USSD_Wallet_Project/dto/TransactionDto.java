package com.glory.USSD_Wallet_Project.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



public class TransactionDto {

    @Data
     @AllArgsConstructor
    @NoArgsConstructor
    public static class DepositRequest {
        @NotBlank(message = "Phone number is required")
        private String phoneNumber;

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Deposit amount must be greater than zero")
        private BigDecimal amount;

        private String description;
    }


     @Data
    public static class WithdrawRequest {
        @NotBlank(message = "Phone number is required")
        private String phoneNumber;

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Withdrawal amount must be greater than zero")
        private BigDecimal amount;

        private String description;
    }

     // CREDIT or DEBIT

    @Data
     @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String type;      
        private BigDecimal amount;
        private String description;
        private String reference;
        private LocalDateTime timestamp;


    }
}




