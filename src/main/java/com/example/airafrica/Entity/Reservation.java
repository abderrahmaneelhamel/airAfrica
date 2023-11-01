package com.example.airafrica.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "traveller_id")
    private Traveller traveller;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reservation_date")
    private Date reservationDate;

    @ManyToOne
    @JoinColumn(name = "flight_class_id")
    private FlightClass flightClass;

    @Column(name = "baggage_weight")
    private int baggageWeight;

    @Column(name = "total_cost")
    private double totalCost;

    @Column(name = "cancellation_status")
    private boolean cancellationStatus;

    public Reservation() {
    }

    public Reservation(Traveller traveller, Flight flight, Date reservationDate, FlightClass flightClass) {
        this.traveller = traveller;
        this.flight = flight;
        this.reservationDate = reservationDate;
        this.flightClass = flightClass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Traveller getTraveller() {
        return traveller;
    }

    public void setTraveller(Traveller traveller) {
        this.traveller = traveller;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public FlightClass getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(FlightClass flightClass) {
        this.flightClass = flightClass;
    }

    public int getBaggageWeight() {
        return baggageWeight;
    }

    public void setBaggageWeight(int baggageWeight) {
        this.baggageWeight = baggageWeight;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isCancellationStatus() {
        return cancellationStatus;
    }

    public void setCancellationStatus(boolean cancellationStatus) {
        this.cancellationStatus = cancellationStatus;
    }
}
