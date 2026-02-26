package com._Comets.bookinghotel_service.repository;

import com._Comets.bookinghotel_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByHotelId(Long hotelId);

    List<Booking> findByCustomerEmail(String email);

    @org.springframework.data.jpa.repository.Query("SELECT b.roomId FROM Booking b WHERE (b.checkInDate <= :checkOut AND b.checkOutDate >= :checkIn)")
    List<Long> findBookedRoomIds(
            @org.springframework.data.repository.query.Param("checkIn") java.time.LocalDate checkIn,
            @org.springframework.data.repository.query.Param("checkOut") java.time.LocalDate checkOut);
}
