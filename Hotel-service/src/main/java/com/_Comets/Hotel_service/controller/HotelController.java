package com._Comets.Hotel_service.controller;

import com._Comets.Hotel_service.entity.Hotel;
import com._Comets.Hotel_service.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelservice;

    public HotelController(HotelService hotelservice) {
        this.hotelservice = hotelservice;
    }

    @PostMapping
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelservice.saveHotel(hotel);
    }

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelservice.getAllHotels();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(hotelservice.getHotelById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(
            @RequestParam(required = false, name = "location") String location) {
        return ResponseEntity.ok(hotelservice.searchHotels(location));
    }
}
