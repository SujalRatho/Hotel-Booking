package com._Comets.bookinghotel_service.client;

import com._Comets.bookinghotel_service.DTO.RoomDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelClient {

    @GetMapping("/rooms/{roomId}")
    RoomDTO getRoomById(@PathVariable("roomId") Long roomId);

    @GetMapping("/hotels/{id}")
    java.util.Map<String, Object> getHotelById(@PathVariable("id") Long id);

    @org.springframework.web.bind.annotation.PutMapping("/rooms/{roomId}/book")
    RoomDTO bookRoom(@PathVariable("roomId") Long roomId);

    @GetMapping("/hotels/search")
    java.util.List<java.util.Map<String, Object>> searchHotels(
            @org.springframework.web.bind.annotation.RequestParam(required = false, name = "location") String location);

    @GetMapping("/rooms/hotel/{hotelId}")
    java.util.List<RoomDTO> getRoomsByHotel(@PathVariable("hotelId") Long hotelId);
}
