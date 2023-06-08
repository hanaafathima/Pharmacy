package com.aspiresys.orderservice.Controller;

import com.aspiresys.orderservice.Entity.Order;
import com.aspiresys.orderservice.Service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    Order order;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper;

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

        objectMapper = new ObjectMapper();

    }

    @Test
    void addOrder() throws Exception {
        Mockito.when(orderService.addOrder(Mockito.any(Order.class))).thenReturn(ResponseEntity.ok(order));
        mockMvc.perform(MockMvcRequestBuilders.post("/pharmacy-management/order/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());

    }

    @Test
    void viewOrder() throws Exception {
        Mockito.when(orderService.viewOrder(Mockito.anyLong())).thenReturn(ResponseEntity.ok(order));

        mockMvc.perform(get("/pharmacy-management/order/{id}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void listAllOrders() throws Exception {
        Mockito.when(orderService.listAllOrders()).thenReturn(ResponseEntity.ok(Arrays.asList(order)));

        mockMvc.perform(get("/pharmacy-management/order/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void searchOrder() throws Exception {
        Mockito.when(orderService.searchOrder(order)).thenReturn(ResponseEntity.ok(Arrays.asList(order)));
        mockMvc.perform(post("/pharmacy-management/order/search",order)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isOk());
    }

    @Test
    void updateOrder() throws Exception{
        Mockito.when(orderService.updateOrder(Mockito.anyLong(),any())).thenReturn(ResponseEntity.ok(order));
        mockMvc.perform(patch("/pharmacy-management/order/change/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void deleteOrder() throws Exception {
        Mockito.when(orderService.deleteOrder(1L)).thenReturn(ResponseEntity.ok(order));
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/pharmacy-management/order/remove/{id}", 1L))
                .andExpect(status().isOk());
    }
}