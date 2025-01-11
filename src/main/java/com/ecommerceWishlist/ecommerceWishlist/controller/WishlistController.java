package com.ecommerceWishlist.ecommerceWishlist.controller;

import com.ecommerceWishlist.ecommerceWishlist.model.enrichments.EnrichedWishlist;
import com.ecommerceWishlist.ecommerceWishlist.model.entities.Wishlist;
import com.ecommerceWishlist.ecommerceWishlist.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RequestMapping("/wishlist")
@RestController
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> register() {
        return ResponseEntity.ok("Wishlist Service Working Properly");
    }

    @PostMapping("")
    public ResponseEntity<Optional<Wishlist>> createWishlist(@RequestBody Wishlist wishlist) {
        Optional<Wishlist> createdWishlist = Optional.of(wishlistService.createWishlist(wishlist));

        if (createdWishlist.isEmpty()) {
            return ResponseEntity.badRequest().body(Optional.empty());
        }

        return ResponseEntity.ok(createdWishlist);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<EnrichedWishlist>> getAllWishlistsFromUser(@PathVariable("userId") String userId) {
        List<EnrichedWishlist> wishlistList = wishlistService.getWishlistsByUserId(userId);

        if (wishlistList.isEmpty()) {
            return ResponseEntity.badRequest().body(wishlistList);
        }

        return ResponseEntity.ok(wishlistList);
    }
    @GetMapping("/{wishlistId}")
    public ResponseEntity<Optional<EnrichedWishlist>> getWishlist(@PathVariable("wishlistId") String wishlistId) {
        Optional<EnrichedWishlist> wishlistFound = wishlistService.getWishlist(wishlistId);

        if (wishlistFound.isEmpty()) {
            return ResponseEntity.badRequest().body(Optional.empty());
        }

        return ResponseEntity.ok(wishlistFound);
    }

    @PutMapping("/Add/{wishlistId}/{productId}")
    public ResponseEntity<Optional<Wishlist>> AddProductToWishlist(@PathVariable("wishlistId") String wishlistId, @PathVariable("productId") String productId){
        Optional<Wishlist> wishlist = wishlistService.addProductToWishlist(wishlistId, productId);

        if(wishlist.isEmpty()){
            return ResponseEntity.badRequest().body(Optional.empty());
        }

        return ResponseEntity.ok(wishlist);
    }
    @PutMapping("/Remove/{wishlistId}/{productId}")
    public ResponseEntity<Optional<Wishlist>> RemoveProductFromWishlist(@PathVariable("wishlistId") String wishlistId, @PathVariable("productId") String productId){
        Optional<Wishlist> wishlist = wishlistService.removeProductFromWishlist(wishlistId, productId);

        if(wishlist.isEmpty()){
            return ResponseEntity.badRequest().body(Optional.empty());
        }

        return ResponseEntity.ok(wishlist);
    }
}
