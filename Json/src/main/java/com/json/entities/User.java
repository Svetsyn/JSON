package com.json.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private List<Product> sellingProducts;
    private List<Product> boughtProducts;
    private Set<User> friends;

    public User() {
        this.sellingProducts = new ArrayList<>();
        this.boughtProducts = new ArrayList<>();
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @OneToMany(targetEntity = Product.class,mappedBy = "seller")
    public List<Product> getSellingProducts() {
        return sellingProducts;
    }

    public void setSellingProducts(List<Product> sellingProducts) {
        this.sellingProducts = sellingProducts;
    }
    @OneToMany(targetEntity = Product.class,mappedBy = "buyer")
    public List<Product> getBoughtProducts() {
        return boughtProducts;
    }

    public void setBoughtProducts(List<Product> boughtProducts) {
        this.boughtProducts = boughtProducts;
    }

    @ManyToMany
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }
}
