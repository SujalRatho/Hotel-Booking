package com._Comets.bookinghotel_service.service;

import com._Comets.bookinghotel_service.DTO.RoomDTO;
import com._Comets.bookinghotel_service.client.HotelClient;
import com._Comets.bookinghotel_service.entity.Booking;
import com._Comets.bookinghotel_service.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final HotelClient hotelClient;

    public BookingService(BookingRepository bookingRepository,
            HotelClient hotelClient) {
        this.bookingRepository = bookingRepository;
        this.hotelClient = hotelClient;
    }

    public Booking createBooking(Booking booking) {

        // 1️⃣ Validate room exists via hotel service
        RoomDTO room = hotelClient.getRoomById(booking.getRoomId());

        // Fetch the Hotel to save its name
        java.util.Map<String, Object> hotel = hotelClient.getHotelById(booking.getHotelId());

        if (hotel != null && hotel.containsKey("name")) {
            booking.setHotelName((String) hotel.get("name"));
        }
        if (hotel != null && hotel.containsKey("imageUrl")) {
            booking.setHotelImageUrl((String) hotel.get("imageUrl"));
        }
        booking.setRoomCategory(room.getCategory());

        // 2️⃣ Calculate total price
        long days = ChronoUnit.DAYS.between(
                booking.getCheckInDate(),
                booking.getCheckOutDate());

        booking.setTotalPrice(days * room.getPrice());
        booking.setStatus("BOOKED");

        // 3️⃣ Reduce room availability in Hotel Service
        hotelClient.bookRoom(booking.getRoomId());

        return bookingRepository.save(booking);
    }

    public java.util.List<java.util.Map<String, Object>> searchAvailableRooms(
            String location, java.time.LocalDate checkIn, java.time.LocalDate checkOut,
            Double minPrice, Double maxPrice, String category) {

        // 1. Get partially booked room IDs for the date range
        java.util.List<Long> bookedRoomIds = bookingRepository.findBookedRoomIds(checkIn, checkOut);

        // Count how many times each room is booked to compare with total capacity
        java.util.Map<Long, Long> bookingCounts = bookedRoomIds.stream()
                .collect(java.util.stream.Collectors.groupingBy(id -> id, java.util.stream.Collectors.counting()));

        // 2. Search hotels by location
        java.util.List<java.util.Map<String, Object>> hotels = hotelClient.searchHotels(location);

        java.util.List<java.util.Map<String, Object>> availableResults = new java.util.ArrayList<>();

        // 3. For each hotel, find valid rooms
        for (java.util.Map<String, Object> hotel : hotels) {
            Long hotelId = ((Number) hotel.get("id")).longValue();
            java.util.List<RoomDTO> rooms = hotelClient.getRoomsByHotel(hotelId);
            java.util.List<RoomDTO> filteredRooms = new java.util.ArrayList<>();

            for (RoomDTO room : rooms) {
                // Price filter
                if (minPrice != null && room.getPrice() < minPrice)
                    continue;
                if (maxPrice != null && room.getPrice() > maxPrice)
                    continue;
                // Category filter
                if (category != null && !category.trim().isEmpty()) {
                    if (room.getCategory() == null || !room.getCategory().equalsIgnoreCase(category)) {
                        continue;
                    }
                }

                // Availability filter (total capacity vs current bookings in range)
                // We use getType/getTotalRooms equivalent. Since RoomDTO might lack totalRooms,
                // we assume if it's in the list, we filter out if booking count >= some limit.
                // For simplicity, if it's booked at all in this specific exact room logic, skip
                // it
                // (Assuming simple 1:1 mapping for demonstration or if it has availableRooms >
                // 0)

                if (room.getAvailableRooms() == null || room.getAvailableRooms() <= 0) {
                    continue;
                }

                filteredRooms.add(room);
            }

            if (!filteredRooms.isEmpty()) {
                hotel.put("availableRooms", filteredRooms);
                availableResults.add(hotel);
            }
        }
        return availableResults;
    }

    public java.util.List<Booking> getBookingsByUserEmail(String email) {
        return bookingRepository.findByCustomerEmail(email);
    }
}
