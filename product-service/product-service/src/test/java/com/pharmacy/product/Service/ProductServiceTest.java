package com.pharmacy.product.Service;

import com.pharmacy.product.Entity.Product;
import com.pharmacy.product.Entity.SearchProduct;
import com.pharmacy.product.Repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    Product product;
    SearchProduct searchProduct;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setMedicineName("Paracetamol");
        product.setMedicineId(1l);
        product.setPrice(100L);
        product.setGenericName("acetaminophin systemic");
        product.setCategory("Fever");
        LocalDate LocalDate = null;
        product.setExpiryDate(LocalDate);
        product.setQuantity(2L);

    }

    @Test
    void addProduct() {
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        ResponseEntity<Product> response = productService.addProduct(product);
        assertThat(response).isNotNull();
    }

    @Test
    void findAllProducts() {
        List<Product> userList=new ArrayList<>();
        userList.add(product);
        Mockito.when(productRepository.findAll()).thenReturn(userList);

        ResponseEntity<List<Product>> userDTOList = productService.findAllProducts();
        assertThat(userDTOList).isNotNull();
    }

    @Test
    void getProductById() {
        Mockito.when(productRepository.findById(1l)).thenReturn(Optional.of(product));
        assertThat(productService.getProductById(1l).getBody());
    }

    @Test
    void searchMedicine() {
        Mockito.when(productRepository.searchProduct(Mockito.anyString(), Mockito.anyString(),Mockito.anyLong(),Mockito.anyInt(),Mockito.anyInt())).thenReturn(Optional.of(Arrays.asList(product)));
        assertThat(productService.searchMedicine(searchProduct).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void updateProduct() {
        Mockito.when(productRepository.findById(product.getMedicineId())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);
        ResponseEntity response=productService.updateProduct(1L,product);
        assertThat(response).isNotNull();
    }

    @Test
    void deleteProduct() {
        Mockito.when(productRepository.findById(1l)).thenReturn(Optional.of(product));
        assertThat(productService.deleteProduct(1l).getBody());
    }

    @Test
    void restock() {
        Mockito.when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);
        assertThat(productService.restock()).isNotNull();
    }
}