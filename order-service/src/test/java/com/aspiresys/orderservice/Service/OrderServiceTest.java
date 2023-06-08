package com.aspiresys.orderservice.Service;

import com.aspiresys.orderservice.Entity.Customer;
import com.aspiresys.orderservice.Entity.Order;
import com.aspiresys.orderservice.Entity.Product;
import com.aspiresys.orderservice.Proxy.FeignCustomerInterface;
import com.aspiresys.orderservice.Proxy.FeignProductInterface;
import com.aspiresys.orderservice.Repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private FeignProductInterface feignProductInterface;
    @Mock
    private FeignCustomerInterface feignCustomerInterface;
    @InjectMocks
    private OrderService orderService;

    Product product;
    Customer customer;
    Order order;


    @BeforeEach
    void setup(){
        order = new Order();
        order.setOrderId(1l);
        order.setMedicineId(1l);
        order.setCustomerId(1l);
        order.setOrders(20l);
        order.setMedicineName("Paracetamol");
        order.setCustomerName("Hana Fathima");
        order.setGenericName("acetaminophin systemic");
        order.setCategory("Fever");

    }

    @Test
    void addOrder() {
        Mockito.when(feignCustomerInterface.findCustomer(1l)).thenReturn(ResponseEntity.ok(customer));
        Mockito.when(feignProductInterface.getProductById(1l)).thenReturn(ResponseEntity.ok(product));
        assertThat(orderService.addOrder(order)).isNotNull();

    }

    @Test
    void viewOrder() {
        Mockito.when(orderRepository.findById(1l)).thenReturn(Optional.of(order));
        assertThat(orderService.viewOrder(1l)).isNotNull();
    }

    @Test
    void listAllOrders() {
        Mockito.when(orderRepository.findAll()).thenReturn(Arrays.asList(order));
        assertThat(orderService.listAllOrders()).isNotNull();
    }

    @Test
    void deleteOrder() {
        Mockito.when(orderRepository.findById(1l)).thenReturn(Optional.of(order));
        assertThat(orderService.deleteOrder(1l)).isNotNull();
    }

    @Test
    void searchOrder() {
        Mockito.when(orderRepository.findAll()).thenReturn(Arrays.asList(order));
        assertThat(orderService.searchOrder(order).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void updateOrder() {
        Mockito.when(orderRepository.findById(order.getMedicineId())).thenReturn(Optional.of(order));
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        ResponseEntity response=orderService.updateOrder(1L,order);
        assertThat(response).isNotNull();
    }
}