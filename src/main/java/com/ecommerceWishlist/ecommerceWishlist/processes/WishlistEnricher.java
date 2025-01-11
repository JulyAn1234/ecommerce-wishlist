package com.ecommerceWishlist.ecommerceWishlist.processes;

import com.ecommerceWishlist.ecommerceWishlist.feigns.CatalogClient;
import com.ecommerceWishlist.ecommerceWishlist.model.enrichments.EnrichedWishlist;
import com.ecommerceWishlist.ecommerceWishlist.model.enrichments.Product;
import com.ecommerceWishlist.ecommerceWishlist.model.entities.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class WishlistEnricher {

    private CatalogClient catalogClient;

    @Autowired
    public WishlistEnricher(CatalogClient catalogClient){
        this.catalogClient = catalogClient;
    }

    public EnrichedWishlist enrichWishlistWithProducts(Wishlist wishlist){
        //iterate for each wishlist's "products" property and perform
        List<Product> products = wishlist.getProducts().stream().map(productId -> catalogClient.getProductFromCatalog(productId)).toList();

        EnrichedWishlist enrichedWishlist = EnrichedWishlist.builder()
                .wishlistId(wishlist.getWishlistId())
                        .name(wishlist.getName())
                                .products(products)
                                        .userId(wishlist.getUserId())
                                                .build();
        return enrichedWishlist;
    }



}
