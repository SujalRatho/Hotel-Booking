package com._Comets.bookinghotel_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BookinghotelServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookinghotelServiceApplication.class, args);
	}

}
