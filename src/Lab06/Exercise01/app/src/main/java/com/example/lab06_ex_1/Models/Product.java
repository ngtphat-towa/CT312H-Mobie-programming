package com.example.lab06_ex_1.Models;

import java.util.ArrayList;

public class Product {
    String name;
    int price;
    int productID;

    public Product(int productID, String name, int price) {
        this.name = name;
        this.price = price;
        this.productID = productID;
    }

    public int getProductId() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public static ArrayList<Product> ImportExampleProduct() {
        ArrayList<Product> products = new ArrayList<>();

        // Add 10 sample products
        products.add(new Product(1, "Shirt", 25));
        products.add(new Product(2, "Jeans", 40));
        products.add(new Product(3, "Shoes", 55));
        products.add(new Product(4, "Hat", 15));
        products.add(new Product(5, "Phone case", 10));
        products.add(new Product(6, "Book", 20));
        products.add(new Product(7, "Headphones", 35));
        products.add(new Product(8, "Watch", 80));
        products.add(new Product(9, "Laptop", 1200));
        products.add(new Product(10, "Coffee mug", 8));

        return products;
    }
}
