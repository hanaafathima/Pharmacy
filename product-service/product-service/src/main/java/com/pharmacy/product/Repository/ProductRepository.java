package com.pharmacy.product.Repository;

import com.pharmacy.product.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long>{

    @Query(value = "select * from product where category like ?1% and name like ?2% and quantity like ?3% limit ?4 offset ?5", nativeQuery = true)
    Optional<Object> searchProduct(String category, String name, Long quantity, Integer pageSize, Integer pageNumber);
}
