package com.aspiresys.customerservice.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity
@Table
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long customerId;
    private String customerName;
    private Long customerContact;
    private String customerAddress;
    private String customerEmail;
}

