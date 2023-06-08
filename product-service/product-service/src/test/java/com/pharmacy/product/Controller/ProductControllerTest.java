package com.pharmacy.product.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pharmacy.product.Entity.Product;
import com.pharmacy.product.Entity.SearchProduct;
import com.pharmacy.product.Service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
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

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ProductControllerTest {

    @MockBean
    private ProductService productService;

    Product product;

    @Autowired
    MockMvc mockMvc;

    SearchProduct searchProduct;
    ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        product = new Product();
        product.setMedicineName("Paracetamol");
        product.setMedicineId(1l);
        product.setPrice(100L);
        product.setGenericName("acetaminophin systemic");
        product.setCategory("Fever");
        LocalDate LocalDate = null;
        product.setExpiryDate(LocalDate);
        product.setQuantity(2L);

        objectMapper = new ObjectMapper();

    }

    @Test
    void addProduct() throws Exception {
        Mockito.when(productService.addProduct(Mockito.any(Product.class))).thenReturn(ResponseEntity.ok(product));
        mockMvc.perform(MockMvcRequestBuilders.post("/pharmacy-management/product/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    void findAllProducts() throws Exception {
        Mockito.when(productService.findAllProducts()).thenReturn(ResponseEntity.ok(Arrays.asList(product)));

        mockMvc.perform(get("/pharmacy-management/product/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getProductById() throws Exception {
        Mockito.when(productService.getProductById(Mockito.anyLong())).thenReturn(ResponseEntity.ok(product));

        mockMvc.perform(MockMvcRequestBuilders.get("/pharmacy-management/product/{id}",1l)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    @Test
    void searchMedicine() throws Exception{
        Mockito.when(productService.searchMedicine(searchProduct)).thenReturn(ResponseEntity.ok(Arrays.asList(product)));
        mockMvc.perform(post("/pharmacy-management/product/search",product)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isOk());
    }

    @Test
    void updateProduct() throws Exception{
        Mockito.when(productService.updateProduct(Mockito.anyLong(),any())).thenReturn(ResponseEntity.ok(product));
        mockMvc.perform(patch("/pharmacy-management/product/change/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void deleteProduct() throws Exception {
        Mockito.when(productService.deleteProduct(1L)).thenReturn(ResponseEntity.ok(product));
        mockMvc.perform(MockMvcRequestBuilders.delete("/pharmacy-management/product/remove/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void restock() {
    }
}