package com.example.airafrica.Entity;

import jakarta.persistence.*;
@Entity
@Table(name = "flight_class")
public class FlightClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name")
    private String className;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return "FlightClass{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", basePrice=" + basePrice +
                '}';
    }

    @Column(name = "base_price")
    private double basePrice;

}

