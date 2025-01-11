package com.ecommerceWishlist.ecommerceWishlist.model.enrichments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String productId;
    private String name;
    private Double price;
    private String imageUrl;

}
