package com._Comets.Hotel_service.service;

import com._Comets.Hotel_service.entity.Hotel;
import com._Comets.Hotel_service.exception.ResourceNotFoundException;
import com._Comets.Hotel_service.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
    }

    public List<Hotel> searchHotels(String location) {
        if (location == null || location.trim().isEmpty()) {
            return hotelRepository.findAll();
        }
        return hotelRepository.findByLocationContainingIgnoreCase(location.trim());
    }
}
