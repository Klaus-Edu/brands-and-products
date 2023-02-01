package com.example.brandsandproducts.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "TB_PRODUCTS")
public class ProductModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String type;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private BrandModel brandModel;

}
