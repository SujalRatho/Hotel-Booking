package com._Comets.Hotel_service.config;

import com._Comets.Hotel_service.entity.Hotel;
import com._Comets.Hotel_service.entity.Room;
import com._Comets.Hotel_service.repository.HotelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

  private final HotelRepository hotelRepository;

  public DataInitializer(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  @Override
  public void run(String... args) {
    // Only seed if database is empty
    if (hotelRepository.count() > 0) {
      System.out.println("Hotels already exist, skipping seed data.");
      return;
    }

    System.out.println("Seeding 10 hotels with rooms...");

    List<Hotel> hotels = new ArrayList<>();

    // Hotel 1
    Hotel h1 = new Hotel();
    h1.setName("The Grand Palace");
    h1.setLocation("Mumbai, India");
    h1.setRating(4.8);
    h1.setImageUrl("https://example.com/images/grand-palace.jpg");
    List<Room> rooms1 = new ArrayList<>();
    rooms1.add(createRoom(2500.0, 20, 15, "Standard", "WiFi, AC, TV", h1));
    rooms1.add(createRoom(4500.0, 10, 7, "Deluxe", "WiFi, AC, TV, Mini Fridge, Breakfast", h1));
    rooms1.add(createRoom(8000.0, 5, 3, "Suite", "WiFi, AC, TV, Mini Fridge, Breakfast, Sea View, Balcony", h1));
    h1.setRooms(rooms1);
    hotels.add(h1);

    // Hotel 2
    Hotel h2 = new Hotel();
    h2.setName("Taj Lakefront");
    h2.setLocation("Udaipur, India");
    h2.setRating(4.9);
    h2.setImageUrl("https://example.com/images/taj-lakefront.jpg");
    List<Room> rooms2 = new ArrayList<>();
    rooms2.add(createRoom(3000.0, 25, 20, "Standard", "WiFi, AC", h2));
    rooms2.add(createRoom(5500.0, 12, 8, "Deluxe", "WiFi, AC, TV, Lake View", h2));
    rooms2.add(createRoom(10000.0, 4, 2, "Presidential Suite", "WiFi, AC, TV, Private Pool, Butler Service", h2));
    h2.setRooms(rooms2);
    hotels.add(h2);

    // Hotel 3
    Hotel h3 = new Hotel();
    h3.setName("Ocean Breeze Resort");
    h3.setLocation("Goa, India");
    h3.setRating(4.5);
    h3.setImageUrl("https://example.com/images/ocean-breeze.jpg");
    List<Room> rooms3 = new ArrayList<>();
    rooms3.add(createRoom(1800.0, 30, 22, "Standard", "WiFi, Fan", h3));
    rooms3.add(createRoom(3500.0, 15, 10, "Deluxe", "WiFi, AC, TV, Beach Access", h3));
    rooms3.add(createRoom(6000.0, 8, 5, "Sea View Suite", "WiFi, AC, TV, Private Balcony, Jacuzzi", h3));
    h3.setRooms(rooms3);
    hotels.add(h3);

    // Hotel 4
    Hotel h4 = new Hotel();
    h4.setName("Mountain View Lodge");
    h4.setLocation("Shimla, India");
    h4.setRating(4.3);
    h4.setImageUrl("https://example.com/images/mountain-view.jpg");
    List<Room> rooms4 = new ArrayList<>();
    rooms4.add(createRoom(2000.0, 18, 14, "Standard", "Heater, TV", h4));
    rooms4.add(createRoom(3800.0, 8, 6, "Deluxe", "Heater, WiFi, TV, View", h4));
    rooms4.add(createRoom(7000.0, 3, 2, "Mountain Suite", "Heater, WiFi, TV, Fireplace, Panoramic View", h4));
    h4.setRooms(rooms4);
    hotels.add(h4);

    // Hotel 5
    Hotel h5 = new Hotel();
    h5.setName("Royal Heritage Inn");
    h5.setLocation("Jaipur, India");
    h5.setRating(4.6);
    h5.setImageUrl("https://example.com/images/royal-heritage.jpg");
    List<Room> rooms5 = new ArrayList<>();
    rooms5.add(createRoom(2200.0, 22, 18, "Standard", "AC, TV", h5));
    rooms5.add(createRoom(4000.0, 10, 7, "Deluxe", "AC, WiFi, TV, Heritage Decor", h5));
    rooms5.add(createRoom(9000.0, 5, 3, "Royal Suite", "AC, WiFi, TV, Four-Poster Bed, Courtyard View", h5));
    h5.setRooms(rooms5);
    hotels.add(h5);

    // Hotel 6
    Hotel h6 = new Hotel();
    h6.setName("Sunrise Beach Hotel");
    h6.setLocation("Chennai, India");
    h6.setRating(4.2);
    h6.setImageUrl("https://example.com/images/sunrise-beach.jpg");
    List<Room> rooms6 = new ArrayList<>();
    rooms6.add(createRoom(1500.0, 35, 28, "Standard", "WiFi, AC", h6));
    rooms6.add(createRoom(3000.0, 15, 12, "Deluxe", "WiFi, AC, TV, Breakfast", h6));
    rooms6.add(createRoom(5500.0, 6, 4, "Beach Suite", "WiFi, AC, TV, Breakfast, Private Terrace", h6));
    h6.setRooms(rooms6);
    hotels.add(h6);

    // Hotel 7
    Hotel h7 = new Hotel();
    h7.setName("City Lights Hotel");
    h7.setLocation("Delhi, India");
    h7.setRating(4.4);
    h7.setImageUrl("https://example.com/images/city-lights.jpg");
    List<Room> rooms7 = new ArrayList<>();
    rooms7.add(createRoom(2800.0, 28, 20, "Standard", "WiFi, AC, TV, Desk", h7));
    rooms7.add(createRoom(5000.0, 14, 10, "Deluxe", "WiFi, AC, TV, Desk, City View", h7));
    rooms7.add(createRoom(9500.0, 6, 4, "Penthouse", "WiFi, AC, TV, Lounge Area, High Floor", h7));
    h7.setRooms(rooms7);
    hotels.add(h7);

    // Hotel 8
    Hotel h8 = new Hotel();
    h8.setName("Backwater Paradise");
    h8.setLocation("Kochi, India");
    h8.setRating(4.7);
    h8.setImageUrl("https://example.com/images/backwater-paradise.jpg");
    List<Room> rooms8 = new ArrayList<>();
    rooms8.add(createRoom(2100.0, 20, 16, "Standard", "Fan, WiFi", h8));
    rooms8.add(createRoom(4200.0, 10, 7, "Deluxe", "AC, WiFi, TV, Backwater View", h8));
    rooms8.add(createRoom(7500.0, 4, 3, "Lagoon Suite", "AC, WiFi, TV, Private Boat Deck", h8));
    h8.setRooms(rooms8);
    hotels.add(h8);

    // Hotel 9
    Hotel h9 = new Hotel();
    h9.setName("Snow Peak Resort");
    h9.setLocation("Manali, India");
    h9.setRating(4.5);
    h9.setImageUrl("https://example.com/images/snow-peak.jpg");
    List<Room> rooms9 = new ArrayList<>();
    rooms9.add(createRoom(1900.0, 16, 12, "Standard", "Heater, Blanket", h9));
    rooms9.add(createRoom(3600.0, 8, 5, "Deluxe", "Heater, WiFi, TV", h9));
    rooms9.add(createRoom(6500.0, 3, 2, "Snow Suite", "Heater, WiFi, TV, Kitchenette", h9));
    h9.setRooms(rooms9);
    hotels.add(h9);

    // Hotel 10
    Hotel h10 = new Hotel();
    h10.setName("Golden Sands Resort");
    h10.setLocation("Pune, India");
    h10.setRating(4.1);
    h10.setImageUrl("https://example.com/images/golden-sands.jpg");
    List<Room> rooms10 = new ArrayList<>();
    rooms10.add(createRoom(1600.0, 24, 20, "Standard", "WiFi, AC", h10));
    rooms10.add(createRoom(3200.0, 12, 9, "Deluxe", "WiFi, AC, TV", h10));
    rooms10.add(createRoom(5800.0, 5, 3, "Premium Suite", "WiFi, AC, TV, Pool Access", h10));
    h10.setRooms(rooms10);
    hotels.add(h10);

    hotelRepository.saveAll(hotels);
    System.out.println("Successfully seeded 10 hotels with rooms!");
  }

  private Room createRoom(Double price, Integer totalRooms, Integer availableRooms, String category, String facilities,
      Hotel hotel) {
    Room room = new Room();
    room.setPrice(price);
    room.setTotalRooms(totalRooms);
    room.setAvailableRooms(availableRooms);
    room.setCategory(category);
    room.setFacilities(facilities);
    room.setHotel(hotel);
    return room;
  }
}
