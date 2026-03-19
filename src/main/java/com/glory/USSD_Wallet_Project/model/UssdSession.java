package com.glory.USSD_Wallet_Project.model;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RedisHash("UssdSession")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UssdSession implements Serializable{


    @Id
    private String sessionId;

     private String phoneNumber;

    private String currentMenu;

    private String pendingInput;

    @TimeToLive
    @Builder.Default
    private long ttl = 120L; //Delete this session after 120 seconds (2 minutes)

    public long getTtl() {
        return ttl;
    }
}

