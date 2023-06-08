package com.pharmacy.product.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Data
@Setter
@Getter
public class Product {
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY,
            generator = "product_sequence"
    )
    private Long medicineId;
    private String medicineName;
    private Long price;
    private Long quantity;
    private String category;
    private LocalDate expiryDate;
    private String genericName;

}

