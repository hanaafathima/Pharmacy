package com.aspiresys.orderservice.Service;

import com.aspiresys.orderservice.Entity.Customer;
import com.aspiresys.orderservice.Entity.Order;
import com.aspiresys.orderservice.Entity.Product;
import com.aspiresys.orderservice.Proxy.FeignCustomerInterface;
import com.aspiresys.orderservice.Proxy.FeignProductInterface;
import com.aspiresys.orderservice.Repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FeignCustomerInterface feignCustomerInterface;

    @Autowired
    private FeignProductInterface feignProductInterface;
    @Value("${server.port}")
    private int port;

    public ResponseEntity<Order> addOrder(Order order) {
        Customer customerDetails = feignCustomerInterface.findCustomer(order.getCustomerId()).getBody();
        System.out.println(customerDetails);
        Product productDetails = feignProductInterface.getProductById(order.getMedicineId()).getBody();
        System.out.println(productDetails);
        order.setCustomerId(order.getCustomerId());
        order.setCustomerName(customerDetails.getCustomerName());
        order.setMedicineName(productDetails.getMedicineName());
        order.setMedicineId(productDetails.getMedicineId());
        order.setGenericName(productDetails.getGenericName());
        order.setCategory(productDetails.getCategory());
        order.setOrders(order.getOrders());
        orderRepository.save(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<List<Order>> listAllOrders() {
        try {
            List<Order> order = orderRepository.findAll();
            if (order.isEmpty()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            } else {
                log.info("List of the orders fetched");
                return new ResponseEntity(order, HttpStatus.OK);
            }
        } catch (Exception exception) {
            ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            return responseEntity;
        }
    }

    public ResponseEntity<Order> viewOrder (Long orderId) {
        Order listOrders = orderRepository.findById(orderId).get();
        System.out.println(port);
        return new ResponseEntity<>(listOrders, HttpStatus.OK);
    }

    public ResponseEntity<Order> deleteOrder(Long orderId) {
        try {
            Optional<Order> deleteuser = orderRepository.findById(orderId);
            if (deleteuser.isPresent()) {
                Order existingUser = deleteuser.get();
//                String email = existingUser.getEmail();
                orderRepository.delete(existingUser);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    public ResponseEntity<List<Order>> searchOrder(Order order) {
        try {
            List orderDetails = orderRepository.findAll()
                    .stream()
                    .filter(showModel -> showModel.getOrderId().equals(Objects.requireNonNullElse(order.getOrderId(), showModel.getOrderId()))
                            && showModel.getMedicineId().equals(Objects.requireNonNullElse(order.getMedicineId(), showModel.getMedicineId()))
                            && showModel.getCustomerId().equals(Objects.requireNonNullElse(order.getCustomerId(), showModel.getCustomerId()))
                            && showModel.getMedicineName().equals(Objects.requireNonNullElse(order.getMedicineName(), showModel.getMedicineName()))
                            && showModel.getCustomerName().equals(Objects.requireNonNullElse(order.getCustomerName(), showModel.getCustomerName()))
                            && showModel.getCategory().equals(Objects.requireNonNullElse(order.getCategory(), showModel.getCategory()))
                            && showModel.getGenericName().equals(Objects.requireNonNullElse(order.getGenericName(), showModel.getGenericName()))
                            && showModel.getOrders().equals(Objects.requireNonNullElse(order.getOrders(), showModel.getOrders()))
                    )
                    .limit(5).toList();

            return ResponseEntity.status(HttpStatus.OK).body(orderDetails);
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Order> updateOrder(Long orderId, Order order) {
        try {
            if(!orderId.equals(null)) {
                Order updateOrder = orderRepository.findById(orderId).get();
                if (order.getOrders() != null) {
                    updateOrder.setOrders(order.getOrders());
                }
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
