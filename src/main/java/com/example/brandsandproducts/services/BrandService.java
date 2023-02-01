package com.example.brandsandproducts.services;

import com.example.brandsandproducts.models.BrandModel;
import com.example.brandsandproducts.repositories.BrandRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository){
        this.brandRepository = brandRepository;
    }

    @Transactional
    public BrandModel save(BrandModel brandModel){
        return brandRepository.save(brandModel);
    }

    public List<BrandModel> findAll(){
        return brandRepository.findAll();
    }

    @Transactional
    public void delete(BrandModel brandModel){
        brandRepository.delete(brandModel);
    }

    public boolean existsByName(String name) {
        return brandRepository.existsByName(name) ;
    }

    public boolean existsByEmail(String email){
        return  brandRepository.existsByEmail(email);
    }

}
