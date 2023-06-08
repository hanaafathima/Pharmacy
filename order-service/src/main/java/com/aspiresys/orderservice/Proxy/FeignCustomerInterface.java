package com.aspiresys.orderservice.Proxy;

import com.aspiresys.orderservice.Entity.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Customer-Service", url="http://localhost:9005/pharmacy-management/customer")
public interface FeignCustomerInterface {
    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomer(@PathVariable(name = "id") Long customerId);
}
