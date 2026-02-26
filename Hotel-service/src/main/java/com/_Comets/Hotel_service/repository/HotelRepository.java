package com._Comets.Hotel_service.repository;

import com._Comets.Hotel_service.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
  List<Hotel> findByLocationContainingIgnoreCase(String location);
}
