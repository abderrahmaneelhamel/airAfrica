package com.example.airafrica.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "travellers")
public class Traveller extends User {
    @Column(name = "loyalty_points")
    private int loyaltyPoints;

    public Traveller() {
        super();
    }

    public Traveller(String username, String email, String password) {
        super(username, email, password);
        this.loyaltyPoints = 0; // Initialize loyalty points to 0
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
}
