package com.example.Project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
private String name;
private double price;
private String category;
private boolean available;

public Product() {}
    public Product(String name, double price, String category, boolean available) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", available=" + available +
                '}';
    }


}

/* Nese perdorim lombokun do te kemi kete strukture kodi ku nuk eshte e nevojshme te shkruajme
getter dhe setter dhe konstruktoret me dhe pa parametra
@Entity
@Data --> per gjenerimin e getters setters dhe toString
@NoArgsConstructor //-->Konstruktori pa parametra
@AllArgsConstructor // -->Kontruktori me parametra
public class Product{
@Id
@GeneratedValue(strategy= GenerationType.AUTO)
private Long id;
private String name;
private double price;
private String category;
private boolean available;
 */
//Pra nese perdorim lombokun eshte e nevojshme qe te shkruajme vetem atributet.
