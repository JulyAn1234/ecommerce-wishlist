package com.ecommerceWishlist.ecommerceWishlist.model.enrichments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrichedWishlist {
    private String wishlistId;
    private String name;
    private String userId;
    private List<Product> products;
}
