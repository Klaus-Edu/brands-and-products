package com.example.brandsandproducts.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDto {

    @NotNull
    private Long brandId;
    @NotBlank
    private String name;
    @NotBlank
    private String type;
}
