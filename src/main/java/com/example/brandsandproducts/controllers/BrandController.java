package com.example.brandsandproducts.controllers;

import com.example.brandsandproducts.dtos.BrandDto;
import com.example.brandsandproducts.models.BrandModel;
import com.example.brandsandproducts.repositories.BrandRepository;
import com.example.brandsandproducts.services.BrandService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/brands")
public class BrandController {

    final BrandService brandService;

    private final BrandRepository brandRepository;

    public BrandController(BrandService brandService, BrandRepository brandRepository){
        this.brandRepository = brandRepository;
        this.brandService = brandService;
    }

    @PostMapping
    public ResponseEntity<Object> saveBrand(@RequestBody @Valid BrandDto brandDto){
        if(brandService.existsByName(brandDto.getName())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Name already registered!");
        }
        if(brandService.existsByEmail(brandDto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already registered!");
        }
        BrandModel brandModel = new BrandModel();

        BeanUtils.copyProperties(brandDto, brandModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.save(brandModel));
    }

    @GetMapping
    public ResponseEntity<List<BrandModel>> getAllBrands(){
        return ResponseEntity.status(HttpStatus.FOUND).body(brandService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findBrandById(@PathVariable(value = "id") Long id){
        Optional<BrandModel> brandModelOptional = brandRepository.findById(id);

        if(!brandModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brand not founded!");
        } else {
            return ResponseEntity.status(HttpStatus.FOUND).body(brandModelOptional.get());
        }
    }

    @PutMapping("/edit/{id}")
    @Transactional
    public ResponseEntity<Object> editBrandById(@PathVariable(value = "id") Long id, @RequestBody BrandDto brandDto){
        Optional<BrandModel> brandModelOptional = brandRepository.findById(id);

        if(!brandModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Couldn't find this brand!");
        } else {
            BrandModel brandModel = brandModelOptional.get();

            brandModel.setName(brandDto.getName());
            brandModel.setBranch(brandDto.getBranch());
            brandModel.setEmail(brandDto.getEmail());

            return ResponseEntity.status(HttpStatus.OK).body(brandService.save(brandModel));
        }
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Object> deleteBrandById(@PathVariable(value = "id") Long id){
        Optional<BrandModel> brandModelOptional = brandRepository.findById(id);

        if(!brandModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Couldn't find this brand!");
        } else {
            brandService.delete(brandModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Brand " + id + " successfully deleted!");
        }

    }
}
