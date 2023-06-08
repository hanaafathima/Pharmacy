package com.aspiresys.customerservice.Controller;

import com.aspiresys.customerservice.Entity.Customer;
import com.aspiresys.customerservice.Entity.Login;
import com.aspiresys.customerservice.Service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest
class CustomerControllerTest {
    @MockBean
    private CustomerService customerService;

    @Autowired
    MockMvc mockMvc;
    Customer customer;
    ObjectMapper objectMapper;
    Login login;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setCustomerEmail("hana@gmail.com");
        customer.setCustomerId(1l);
        customer.setCustomerContact(9890346784l);
        customer.setCustomerName("Hana Fathima");
        customer.setCustomerAddress("Olive Courtyard");

        login = new Login();
        login.setId(1l);
        login.setEmail("hana@gmail.com");
        login.setRole("USER");
        login.setPassword("USERHana9856098222");

    }

    @Test
    void saveCustomer() throws Exception {
        Mockito.when(customerService.saveCustomer(Mockito.any(Customer.class))).thenReturn(ResponseEntity.ok(customer));
        mockMvc.perform(MockMvcRequestBuilders.post("/pharmacy-management/customer/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    void getCredentials() throws Exception{
        Mockito.when(customerService.getCredentials(Mockito.any())).thenReturn(login);
        mockMvc.perform(MockMvcRequestBuilders.get("/pharmacy-management/customer/login/{email}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    void findCustomer() throws Exception{
        Mockito.when(customerService.findCustomer(Mockito.anyLong())).thenReturn(ResponseEntity.ok(customer));

        mockMvc.perform(MockMvcRequestBuilders.get("/pharmacy-management/customer/{id}",1l)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void listAllCustomers() throws Exception {
        Mockito.when(customerService.listAllCustomers()).thenReturn(ResponseEntity.ok(Arrays.asList(customer)));

        mockMvc.perform(MockMvcRequestBuilders.get("/pharmacy-management/customer/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }

    @Test
    void searchCustomer() throws Exception {
        Mockito.when(customerService.searchCustomer(customer)).thenReturn(ResponseEntity.ok(Arrays.asList(customer)));
        mockMvc.perform(MockMvcRequestBuilders.post("/pharmacy-management/customer/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateCustomer() throws Exception {
        Mockito.when(customerService.updateCustomer(Mockito.anyLong(),Mockito.any())).thenReturn(ResponseEntity.ok(customer));
        mockMvc.perform(MockMvcRequestBuilders.patch("/pharmacy-management/customer/change/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Hana"));
    }

    @Test
    void deleteCustomer() throws Exception {
        Mockito.when(customerService.deleteCustomer(1L)).thenReturn(ResponseEntity.ok(customer));
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/pharmacy-management/customer/remove/{id}", 1L))
                .andExpect(status().isOk());

    }
}