package com._Comets.bookinghotel_service.controller;

import com._Comets.bookinghotel_service.entity.Booking;
import com._Comets.bookinghotel_service.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @GetMapping("/search")
    public ResponseEntity<java.util.List<java.util.Map<String, Object>>> searchRooms(
            @RequestParam(required = false, name = "location") String location,
            @RequestParam(required = false, name = "checkIn") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate checkIn,
            @RequestParam(required = false, name = "checkOut") @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate checkOut,
            @RequestParam(required = false, name = "minPrice") Double minPrice,
            @RequestParam(required = false, name = "maxPrice") Double maxPrice,
            @RequestParam(required = false, name = "category") String category) {

        if (checkIn == null)
            checkIn = java.time.LocalDate.now();
        if (checkOut == null)
            checkOut = checkIn.plusDays(1);

        return ResponseEntity
                .ok(bookingService.searchAvailableRooms(location, checkIn, checkOut, minPrice, maxPrice, category));
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<java.util.List<Booking>> getUserBookings() {
        // Since we are mocking user email in the frontend for now, we fetch by it
        return ResponseEntity.ok(bookingService.getBookingsByUserEmail("user@example.com"));
    }
}
