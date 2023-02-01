package com.example.brandsandproducts.repositories;

import com.example.brandsandproducts.models.BrandModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<BrandModel, Long> {
    boolean existsByName(String name);

    boolean existsByEmail(String email);
}
