package com.example.airafrica.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin extends User{

    public Admin(){
        super();
    }
    public Admin(String username, String email, String password) {
        super(username,email,password);
    }
}
