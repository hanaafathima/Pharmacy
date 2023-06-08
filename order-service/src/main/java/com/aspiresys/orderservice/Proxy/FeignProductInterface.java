package com.aspiresys.orderservice.Proxy;

import com.aspiresys.orderservice.Entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Product-Service", url="http://localhost:9006/pharmacy-management/product")
public interface FeignProductInterface {
    @GetMapping(path = "/{id}")
    public ResponseEntity <Product> getProductById(@PathVariable(name="id") Long medicineId);

}
