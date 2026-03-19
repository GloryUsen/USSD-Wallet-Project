package com.glory.USSD_Wallet_Project.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class UssdSessionRequest {

    private String sessionId;
    private String phoneNumber;
    private String text;
    private String networkCode;
    private String serviceCode;


}
