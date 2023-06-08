package com.pharmacy.product.Controller;

import com.pharmacy.product.Entity.Product;
import com.pharmacy.product.Entity.SearchProduct;
import com.pharmacy.product.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy-management/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/new")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> findAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity <Product> getProductById(@PathVariable(name="id") Long medicineId) {
        return productService.getProductById(medicineId);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Product>> searchMedicine(@RequestBody SearchProduct searchProduct) {
        return productService.searchMedicine(searchProduct);
    }

    @PatchMapping("/change/{id}")
    public  ResponseEntity<Product> updateProduct(@PathVariable(name="id") Long medicineId , @RequestBody Product product)
    {
        return productService.updateProduct(medicineId ,product);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable(name="id") Long medicineId) {
        return productService.deleteProduct(medicineId);
    }

    @GetMapping("/restocklist")
    public ResponseEntity<List<Product>> restock() {
        return productService.restock();
    }

}