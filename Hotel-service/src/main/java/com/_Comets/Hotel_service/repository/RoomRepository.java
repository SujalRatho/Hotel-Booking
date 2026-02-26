package com._Comets.Hotel_service.repository;

import com._Comets.Hotel_service.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long > {
    List<Room> findByHotelId(Long hotelId);
}
