package com.glory.USSD_Wallet_Project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@Builder
@Table(name = "account_user")
@NoArgsConstructor
@AllArgsConstructor


public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(nullable = true)
    private String firstname;

    @Column(nullable = true)
    private String lastname;

    @Column(nullable = true)
    private String middlename;

    @Column(nullable = true)
    private String username;


    @Column(nullable = false, unique = true)
    private String phoneNumber;


    @Column(nullable = false, unique = true)
    private String accountNumber;
}
