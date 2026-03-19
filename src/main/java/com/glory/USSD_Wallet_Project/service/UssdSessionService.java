// path: com/glory/USSD_Wallet_Project/service/UssdSessionService.java

package com.glory.USSD_Wallet_Project.service;

import org.springframework.stereotype.Service;

import com.glory.USSD_Wallet_Project.dto.UssdSessionRequest;
import com.glory.USSD_Wallet_Project.model.UssdSession;
import com.glory.USSD_Wallet_Project.repository.UssdSessionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UssdSessionService {

    private final UssdSessionRepository sessionRepository;
    private final TransactionService transactionService;

    public String handleRequest(UssdSessionRequest request) {

        String sessionId = request.getSessionId();
        String phone = request.getPhoneNumber();
        String text = request.getText();

        UssdSession session = sessionRepository.findById(sessionId)
                .orElse(UssdSession.builder()
                        .sessionId(sessionId)
                        .phoneNumber(phone)
                        .currentMenu("MAIN")
                        .build());

        if (text == null || text.isEmpty()) {
            return mainMenu();
        }

        String[] inputs = text.split("\\*");

        switch (inputs[0]) {

            case "1": // Deposit
                if (inputs.length == 1) {
                    return "CON Enter amount:";
                }
                return deposit(phone, inputs[1]);

            case "2": // Withdraw
                if (inputs.length == 1) {
                    return "CON Enter amount:";
                }
                return withdraw(phone, inputs[1]);

            case "3": // Balance
                return balance(phone);

            case "4": // History
                return history(phone);

            default:
                return "END Invalid option";
        }
    }

    private String mainMenu() {
        return "CON Welcome\n1. Deposit\n2. Withdraw\n3. Check Balance\n4. Transaction History";
    }

    private String deposit(String phone, String amountStr) {
        try {
            var req = new com.glory.USSD_Wallet_Project.dto.TransactionDto.DepositRequest();
            req.setPhoneNumber(phone);
            req.setAmount(new java.math.BigDecimal(amountStr));

            var res = transactionService.deposit(req);
            return "END Deposit successful: " + res.getAmount();

        } catch (Exception e) {
            return "END " + e.getMessage();
        }
    }

    private String withdraw(String phone, String amountStr) {
        try {
            var req = new com.glory.USSD_Wallet_Project.dto.TransactionDto.WithdrawRequest();
            req.setPhoneNumber(phone);
            req.setAmount(new java.math.BigDecimal(amountStr));

            var res = transactionService.withdraw(req);
            return "END Withdraw successful: " + res.getAmount();

        } catch (Exception e) {
            return "END " + e.getMessage();
        }
    }

    private String balance(String phone) {
        try {
            return "END Balance: " + transactionService.checkBalance(phone);
        } catch (Exception e) {
            return "END " + e.getMessage();
        }
    }

    private String history(String phone) {
        try {
            var list = transactionService.getTransactionHistory(phone);

            if (list.isEmpty()) {
                return "END No transactions found";
            }

            StringBuilder sb = new StringBuilder("END Transactions:\n");
            list.forEach(tx ->
                    sb.append(tx.getType())
                      .append(" ")
                      .append(tx.getAmount())
                      .append("\n")
            );

            return sb.toString();

        } catch (Exception e) {
            return "END " + e.getMessage();
        }
    }
}