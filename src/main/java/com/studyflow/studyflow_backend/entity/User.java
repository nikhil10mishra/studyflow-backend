package com.studyflow.studyflow_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;   // Google UID or Firebase UID

    private String email;

    private String name;

    public User() {}

    public User(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}