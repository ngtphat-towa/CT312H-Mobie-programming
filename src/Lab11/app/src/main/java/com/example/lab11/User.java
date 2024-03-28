package com.example.lab11;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String address;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
