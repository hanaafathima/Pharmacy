package com.aspiresys.orderservice.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@Setter
@ToString
public class Customer {
    private Long customerId;
    private String customerName;
    private BigInteger customerContact;
    private String customerAddress;
    private String customerEmail;
}
