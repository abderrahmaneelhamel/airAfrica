package com.example.airafrica.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reservation_extras")
public class ReservationExtras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Reservation reservation;

    @ManyToOne
    private Extras extra;

    public ReservationExtras(){

    }

    public ReservationExtras(Long id, Reservation reservation, Extras extra) {
        this.id = id;
        this.reservation = reservation;
        this.extra = extra;
    }
    public ReservationExtras(Reservation reservation, Extras extra) {
        this.reservation = reservation;
        this.extra = extra;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Extras getExtra() {
        return extra;
    }

    public void setExtra(Extras extra) {
        this.extra = extra;
    }
}
