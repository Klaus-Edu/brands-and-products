package com.example.brandsandproducts.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BrandDto {

    @NotBlank
    private  String name;
    @NotBlank
    private String branch;
    @NotBlank
    @Email
    private String email;
}
