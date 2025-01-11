package com.ecommerceWishlist.ecommerceWishlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EcommerceWishlistApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcommerceWishlistApplication.class, args);
	}
}
