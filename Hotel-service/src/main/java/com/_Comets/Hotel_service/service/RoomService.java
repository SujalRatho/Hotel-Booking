package com._Comets.Hotel_service.service;

import com._Comets.Hotel_service.entity.Room;
import com._Comets.Hotel_service.exception.ResourceNotFoundException;
import com._Comets.Hotel_service.repository.HotelRepository;
import com._Comets.Hotel_service.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    public final HotelRepository hotelRepository;

    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));
    }

    public Room bookRoom(Long roomId) {
        Room room = getRoomById(roomId);
        if (room.getAvailableRooms() <= 0) {
            throw new IllegalStateException("No available rooms in this category.");
        }
        room.setAvailableRooms(room.getAvailableRooms() - 1);
        return roomRepository.save(room);
    }

    public List<Room> getRoomsByHotelId(Long hotelId) {

        if (!hotelRepository.existsById(hotelId)) {
            throw new ResourceNotFoundException("Hotel not found with id: " + hotelId);
        }

        return roomRepository.findByHotelId(hotelId);
    }
}
