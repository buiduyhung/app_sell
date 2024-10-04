package com.example.appsell.model;

public class Product {
    int id;
    String name;
    String image;
    String price;
    String content;
    int categoryProduct;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategoryProduct(int categoryProduct) {
        this.categoryProduct = categoryProduct;
    }
}
