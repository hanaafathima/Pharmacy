package com.aspiresys.orderservice.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Long customerId;
    private String customerName;
    private Long medicineId;
    private String medicineName;
    private String category;
    private String genericName;
    private Long orders;
}
