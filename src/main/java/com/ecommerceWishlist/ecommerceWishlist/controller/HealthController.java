package com.ecommerceWishlist.ecommerceWishlist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequestMapping("/")
@RestController
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<String> register() {
        return ResponseEntity.ok("Wishlist Service Working Properly");
    }
}
