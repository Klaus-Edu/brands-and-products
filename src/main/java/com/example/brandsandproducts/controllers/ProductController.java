package com.example.brandsandproducts.controllers;

import com.example.brandsandproducts.dtos.ProductDto;
import com.example.brandsandproducts.models.BrandModel;
import com.example.brandsandproducts.models.ProductModel;
import com.example.brandsandproducts.repositories.BrandRepository;
import com.example.brandsandproducts.repositories.ProductsRepository;
import com.example.brandsandproducts.services.ProductService;
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
@RequestMapping("/products")
public class ProductController {

    final ProductService productService;

    private final ProductsRepository productsRepository;
    private final BrandRepository brandRepository;

    public ProductController(ProductsRepository productsRepository, ProductService productService,
                             BrandRepository brandRepository){
        this.productService = productService;
        this.productsRepository = productsRepository;
        this.brandRepository = brandRepository;
    }

    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductDto productDto){

        if(productService.existsByName(productDto.getName())){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Name already registered!");
        }

        ProductModel productModel = new ProductModel();

        BeanUtils.copyProperties(productDto, productModel);
        Optional<BrandModel> brandModelOptional = brandRepository.findById(productDto.getBrandId());

        productModel.setBrandModel(brandModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productModel));
    }

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.FOUND).body(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findProductById(@PathVariable(value = "id") Long id){
        Optional<ProductModel> productModelOptional = productsRepository.findById(id);

        if(!productModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not founded!");
        } else {
            return ResponseEntity.status(HttpStatus.FOUND).body(productModelOptional.get());
        }
    }

    @PutMapping("/edit/{id}")
    @Transactional
    public ResponseEntity<Object> editProductById(@PathVariable(value = "id") Long id, @RequestBody ProductDto productDto){
        Optional<ProductModel> productModelOptional = productsRepository.findById(id);

        if(!productModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Couldn't find this product!");
        } else {
            ProductModel productModel = productModelOptional.get();
            Optional<BrandModel> brandModelOptional = brandRepository.findById(productDto.getBrandId());

            productModel.setName(productDto.getName());
            productModel.setType(productDto.getType());
            productModel.setBrandModel(brandModelOptional.get());

            return ResponseEntity.status(HttpStatus.OK).body(productService.save(productModel));
        }
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Object> deleteProductById(@PathVariable(value = "id") Long id){
        Optional<ProductModel> productModelOptional = productsRepository.findById(id);

        if(!productModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Couldn't find this product!");
        } else {
            productService.delete(productModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("Product " + id + " successfully deleted!");
        }

    }
}
