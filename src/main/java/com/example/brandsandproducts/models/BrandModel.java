package com.example.brandsandproducts.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "TB_BRAND")
public class BrandModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 200)
    private String name;
    @Column(nullable = false)
    private String branch;
    @Column(nullable = false, unique = true)
    private String email;
    @OneToMany(mappedBy = "brandModel")
    @JsonManagedReference
    private List<ProductModel> productModel;


}
