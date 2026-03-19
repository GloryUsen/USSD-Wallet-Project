package com.glory.USSD_Wallet_Project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class AccountDto { 


    @AllArgsConstructor
    @NoArgsConstructor
    

    @Data
     public static class CreateRequest {
        

         @NotBlank(message = "Phone number is required")
          @Pattern(regexp = "^\\+?[1-9]\\d{7,14}$", message = "Invalid phone number format")

          public String phoneNumber; // Take something unique from someone.
           // private String currency = "NGN"; 

           }

       @Data 
        @AllArgsConstructor
       @NoArgsConstructor
       // The reason for this class, if since we do not have much time in ussd, if a user wants to update inform in physical bank. 

          public static class UpdateRequest {
           

            @NotBlank(message = "Name is required")
              private String firstName; 
              private String lastName; 


              @NotBlank(message = "Phone number is required")
              @Pattern(regexp = "^\\+?[1-9]\\d{7,14}$", message = "Invalid phone number format")
                private String phoneNumber;   // Take something unique from someone. 
                private String currency = "NGN";
          }

            
                 @Data 
                 public static class Response { 
                
                    private Long id; 
                    private String name;
                     private String phoneNumber;
                      private java.math.BigDecimal balance; 
                      private String currency; 
                      private java.time.LocalDateTime createdAt; 
                    }


                     @Data class BalanceResponse { 
                        private String phoneNumber; 
                        private String name; 
                        private java.math.BigDecimal balance; 
                        private String currency; 
                    }
                }
    
        