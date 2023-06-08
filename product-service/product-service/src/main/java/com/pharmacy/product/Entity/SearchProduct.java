package com.pharmacy.product.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
@Getter
public class SearchProduct {
    private Long medicineId;
    private String medicineName;
    private Long price;
    private Long quantity;
    private String category;
    private LocalDate expiryDate;
    private String genericName;
    private Integer pageSize;
    private Integer pageNumber;
}
