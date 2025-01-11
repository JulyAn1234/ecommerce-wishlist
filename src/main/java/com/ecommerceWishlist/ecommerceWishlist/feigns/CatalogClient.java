package com.ecommerceWishlist.ecommerceWishlist.feigns;

import com.ecommerceWishlist.ecommerceWishlist.model.enrichments.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ecommerce-catalog-public", url = "http://localhost/ecommerce-catalog-public")
public interface CatalogClient {
    @GetMapping("/product/{productId}")
    Product getProductFromCatalog(@PathVariable("productId") String productId);
}
