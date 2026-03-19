// path: com/glory/USSD_Wallet_Project/controller/UssdSessionController.java

package com.glory.USSD_Wallet_Project.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.glory.USSD_Wallet_Project.dto.UssdSessionRequest;
import com.glory.USSD_Wallet_Project.service.UssdSessionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UssdSessionController {

    private final UssdSessionService ussdSessionService;

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String handleUssd(
            @RequestParam(value = "sessionId", required = false) String sessionId,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "text", required = false, defaultValue = "") String text,
            @RequestParam(value = "networkCode", required = false) String networkCode,
            @RequestParam(value = "serviceCode", required = false) String serviceCode,
            @RequestBody(required = false) UssdSessionRequest jsonBody
    ) {

        UssdSessionRequest request = buildRequest(sessionId, phoneNumber, text, networkCode, serviceCode, jsonBody);

        log.info("Incoming USSD | session={} phone={} text='{}'",
                request.getSessionId(), request.getPhoneNumber(), request.getText());

        return ussdSessionService.handleRequest(request);
    }

    private UssdSessionRequest buildRequest(String sessionId, String phoneNumber, String text,
                                            String networkCode, String serviceCode,
                                            UssdSessionRequest jsonBody) {

        if (jsonBody != null && jsonBody.getSessionId() != null) {
            return jsonBody;
        }

        UssdSessionRequest req = new UssdSessionRequest();
        req.setSessionId(sessionId);
        req.setPhoneNumber(phoneNumber);
        req.setText(text);
        req.setNetworkCode(networkCode);
        req.setServiceCode(serviceCode);
        

        return req;
    }
}