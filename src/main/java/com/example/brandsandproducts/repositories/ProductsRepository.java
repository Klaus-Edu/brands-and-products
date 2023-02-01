package com.example.brandsandproducts.repositories;

import com.example.brandsandproducts.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<ProductModel, Long> {
    boolean existsByName(String name);
}
