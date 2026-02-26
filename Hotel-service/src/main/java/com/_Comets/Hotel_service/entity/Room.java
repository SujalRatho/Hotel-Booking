package com._Comets.Hotel_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private Integer totalRooms;
    private Integer availableRooms;
    private String category;
    private String facilities;

    public Room() {
    }

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonBackReference
    private Hotel hotel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(Integer totalRooms) {
        this.totalRooms = totalRooms;
    }

    public Integer getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(Integer availableRooms) {
        this.availableRooms = availableRooms;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Room(Long id, Double price, Integer totalRooms, Integer availableRooms, String category, String facilities,
            Hotel hotel) {
        this.id = id;
        this.price = price;
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.category = category;
        this.facilities = facilities;
        this.hotel = hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}