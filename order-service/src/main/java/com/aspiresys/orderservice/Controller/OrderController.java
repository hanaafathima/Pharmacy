package com.aspiresys.orderservice.Controller;

import com.aspiresys.orderservice.Entity.Order;
import com.aspiresys.orderservice.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/pharmacy-management/order")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/new")
    public ResponseEntity<Order> addOrder(@RequestBody Order order)
    {
        return orderService.addOrder(order);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Order>> listAllOrders()
    {
        return orderService.listAllOrders();
    }


    @GetMapping("/{id}")
    public ResponseEntity <Order> viewOrder(@PathVariable(name="id") Long orderId) {
        return orderService.viewOrder(orderId);
    }
    @PostMapping("/search")
    public ResponseEntity<List<Order>> searchOrder(@RequestBody Order order) {
        return orderService.searchOrder(order);
    }

    @PatchMapping("/change/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable(name="id") Long orderId , @RequestBody Order order){
        log.info("Updated Customer details successfully");
        return orderService.updateOrder(orderId , order);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable("id") Long orderId) {
        log.info("Deleted Order details successfully");
        return orderService.deleteOrder(orderId);
    }

}
