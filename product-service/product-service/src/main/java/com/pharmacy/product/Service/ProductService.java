package com.pharmacy.product.Service;

import com.pharmacy.product.Entity.SearchProduct;
import com.pharmacy.product.Repository.ProductRepository;
import com.pharmacy.product.Entity.Product;
import com.pharmacy.product.ServiceInterface.ProductServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService implements ProductServiceInterface {
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<Product> addProduct(Product product) {
        try {
            LocalDate today = LocalDate.now();
            LocalDate expiryDate = today.plusMonths(6);
            product.setExpiryDate(expiryDate);
            productRepository.save(product);
            log.info("Products added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
    public ResponseEntity<List<Product>> findAllProducts() {
        try {
            List<Product> productList = productRepository.findAll();
            if (!productList.isEmpty()) {
                log.info("List of products fetched successfully");
                return new ResponseEntity(productList, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Product> getProductById(Long medicineId) {
                Product getProduct = productRepository.findById(medicineId).get();
             return new ResponseEntity<>(getProduct,HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> searchMedicine(SearchProduct searchProduct) {
        Integer realPage = searchProduct.getPageNumber() * searchProduct.getPageSize();
        searchProduct.setPageNumber(realPage);
        try {
            List productDetails = productRepository.findAll()
                    .stream()
                    .filter(showModel -> showModel.getMedicineName().equals(Objects.requireNonNullElse(searchProduct.getMedicineName(), showModel.getMedicineName()))
                            && showModel.getQuantity().equals(Objects.requireNonNullElse(searchProduct.getQuantity(), showModel.getQuantity()))
                            && showModel.getPrice().equals(Objects.requireNonNullElse(searchProduct.getPrice(), showModel.getPrice()))
                            && showModel.getCategory().equals(Objects.requireNonNullElse(searchProduct.getCategory(), showModel.getCategory()))
                            && showModel.getGenericName().equals(Objects.requireNonNullElse(searchProduct.getGenericName(), showModel.getGenericName()))
                    )
                    .limit(5).toList();

            return ResponseEntity.status(HttpStatus.OK).body(productDetails);
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<Product> updateProduct(Long medicineId, Product product) {
        try {
            if(!medicineId.equals(null)) {
                Product updateProduct = productRepository.findById(medicineId).get();
                if (product.getMedicineName() != null) {
                    updateProduct.setMedicineName(product.getMedicineName());
                }
                if (product.getCategory() !=null){
                    updateProduct.setCategory(product.getCategory());
                }
                if (product.getPrice() !=null){
                    updateProduct.setPrice(product.getPrice());
                }
                if (product.getQuantity() !=null){
                    updateProduct.setQuantity(product.getQuantity());
                }
                if (product.getGenericName() !=null){
                    updateProduct.setGenericName(product.getGenericName());
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

    public ResponseEntity<Product> deleteProduct(Long medicineId) {
        try {
            List product = productRepository.findAll().stream()
                    .filter(item-> item.getMedicineId().equals(medicineId))
                    .collect(Collectors.toList());
            if(!product.isEmpty()) {
                productRepository.deleteById(medicineId);
                return new ResponseEntity<Product>(HttpStatus.OK);

            } else {
                return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    public ResponseEntity<List<Product>> restock() {
        try {
            List<Product> productList = productRepository.findAll().stream()
                    .filter(product -> product.getQuantity() < 100)
                    .collect(Collectors.toList());
            productList.forEach(product -> {
                product.setQuantity(product.getQuantity() + 100);
                productRepository.save(product);
            });
            log.info("Products restocked");
            return ResponseEntity.status(HttpStatus.OK).body(productList);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }
}


