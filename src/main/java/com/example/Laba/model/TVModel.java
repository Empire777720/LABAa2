package com.example.Laba.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tv_models")
public class TVModel {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private float price;
    private String imageUrl;
    private String productUrl;

    public TVModel(String title, float price, String imageUrl, String productUrl) {
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
        this.productUrl = productUrl;
    }

    public TVModel() {

    }
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }


}