package com._Comets.Hotel_service.controller;

import com._Comets.Hotel_service.entity.Hotel;
import com._Comets.Hotel_service.entity.Room;
import com._Comets.Hotel_service.service.HotelService;
import com._Comets.Hotel_service.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final HotelService hotelService;
    private final RoomService roomService;

    public RoomController(HotelService hotelService, RoomService roomService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
    }

    @PostMapping("/{hotelId}")
    public ResponseEntity<Room> addRoom(@PathVariable("hotelId") Long hotelId,
            @RequestBody Room room) {

        Hotel hotel = hotelService.getHotelById(hotelId);

        room.setHotel(hotel);

        Room savedRoom = roomService.saveRoom(room);

        return ResponseEntity.ok(savedRoom);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable("roomId") Long roomId) {
        Room room = roomService.getRoomById(roomId);
        return ResponseEntity.ok(room);
    }

    @PutMapping("/{roomId}/book")
    public ResponseEntity<Room> bookRoom(@PathVariable("roomId") Long roomId) {
        Room bookedRoom = roomService.bookRoom(roomId);
        return ResponseEntity.ok(bookedRoom);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Room>> getRoomsByHotel(@PathVariable("hotelId") Long hotelId) {

        List<Room> rooms = roomService.getRoomsByHotelId(hotelId);

        return ResponseEntity.ok(rooms);
    }
}