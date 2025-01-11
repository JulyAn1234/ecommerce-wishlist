package com.ecommerceWishlist.ecommerceWishlist.service;

import com.ecommerceWishlist.ecommerceWishlist.model.enrichments.EnrichedWishlist;
import com.ecommerceWishlist.ecommerceWishlist.model.entities.Wishlist;
import com.ecommerceWishlist.ecommerceWishlist.model.repositories.WishlistRepository;
import com.ecommerceWishlist.ecommerceWishlist.processes.WishlistEnricher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    private WishlistEnricher wishlistEnricher;

    private WishlistRepository wishlistRepository;
    @Autowired
    WishlistService(WishlistEnricher wishlistEnricher, WishlistRepository wishlistRepository){
        this.wishlistEnricher = wishlistEnricher;
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist createWishlist(Wishlist wishlist){
        return wishlistRepository.save(wishlist);
    }

    public void deleteWishlist(String wishlistId){
        wishlistRepository.deleteWishlist(wishlistId);
    }

    public Optional<EnrichedWishlist> getWishlist (String wishlistId) {

        Optional<Wishlist> wishlist = wishlistRepository.getWishlist(wishlistId);

        return wishlist.map(value -> wishlistEnricher.enrichWishlistWithProducts(value));
    }

    public List<EnrichedWishlist> getWishlistsByUserId (String userId) {

        List<Wishlist> wishlists = wishlistRepository.getWishlistsByUserId(userId);

        if(!wishlists.isEmpty()){
            return wishlists.stream().map(wishlist -> wishlistEnricher.enrichWishlistWithProducts(wishlist)).toList();
        }else{
            return List.of();
        }
    }

    public Optional<Wishlist> addProductToWishlist(String wishlistId, String productId){
        return wishlistRepository.addProductToWishlist(wishlistId, productId);
    }

    public Optional<Wishlist> removeProductFromWishlist(String wishlistId, String productId){
        return wishlistRepository.removeProductFromWishlist(wishlistId, productId);
    }



}

