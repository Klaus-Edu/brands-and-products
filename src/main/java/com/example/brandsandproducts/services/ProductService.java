package com.example.brandsandproducts.services;

import com.example.brandsandproducts.models.ProductModel;
import com.example.brandsandproducts.repositories.ProductsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    final ProductsRepository productsRepository;

    public ProductService(ProductsRepository productsRepository){
        this.productsRepository = productsRepository;
    }

    @Transactional
    public ProductModel save(ProductModel productModel){
        return productsRepository.save(productModel);
    }

    public List<ProductModel> findAll(){
        return productsRepository.findAll();
    }

    @Transactional
    public void delete(ProductModel productModel){
        productsRepository.delete(productModel);
    }

    public boolean existsByName(String name){
        return  productsRepository.existsByName(name);
    }
}
