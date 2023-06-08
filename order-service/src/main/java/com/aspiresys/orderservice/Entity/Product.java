package com.aspiresys.orderservice.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Product {
    private Long medicineId;
    private String medicineName;
    private Long price;
    private Long quantity;
    private String category;
    private LocalDate expiryDate;
    private String genericName;
}
