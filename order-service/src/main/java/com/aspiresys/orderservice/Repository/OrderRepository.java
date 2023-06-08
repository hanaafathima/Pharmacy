package com.aspiresys.orderservice.Repository;

import com.aspiresys.orderservice.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order , Long> {
}
