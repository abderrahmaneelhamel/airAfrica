package com.example.airafrica.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "travellers")
public class Traveller extends User{

    public Traveller(){
        super();
    }
    public Traveller(String username,String email,String password) {
        super(username,email,password);
    }

}
