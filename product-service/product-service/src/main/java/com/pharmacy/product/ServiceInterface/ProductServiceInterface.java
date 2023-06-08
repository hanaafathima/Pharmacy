package com.pharmacy.product.ServiceInterface;

import com.pharmacy.product.Entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductServiceInterface {
    public ResponseEntity<List<Product>> findAllProducts();
    public ResponseEntity<Product> addProduct(Product product);
    public ResponseEntity<Product> updateProduct(Long medicineId, Product product);
    public ResponseEntity<Product> deleteProduct(Long medicineId);
    public ResponseEntity<Product> getProductById(Long medicineId);
    public ResponseEntity<List<Product>> restock();

}
