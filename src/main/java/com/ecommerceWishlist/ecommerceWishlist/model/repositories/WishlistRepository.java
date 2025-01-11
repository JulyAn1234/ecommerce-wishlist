package com.ecommerceWishlist.ecommerceWishlist.model.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import com.ecommerceWishlist.ecommerceWishlist.model.entities.Wishlist;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class WishlistRepository {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Wishlist save(Wishlist Wishlist) {
        dynamoDBMapper.save(Wishlist);
        return Wishlist;
    }

    public Optional<Wishlist> getWishlist(String wishlistId) {
        Wishlist wishlist = null;
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":wishlistId", new AttributeValue().withS(wishlistId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("wishlistId = :wishlistId")
                .withExpressionAttributeValues(eav);

        try {
            List<Wishlist> useResult = dynamoDBMapper.scan(Wishlist.class, scanExpression);
            if (!useResult.isEmpty()) {
                wishlist = useResult.get(0);
                System.out.println("Wishlist found: " + wishlist.getWishlistId());
            } else {
                System.out.println("Wishlist not found.");
            }
        } catch (Exception e) {
            System.err.println("Error during DynamoDB scan:");
            e.printStackTrace(); // Print stack trace for debugging
        }

        return Optional.ofNullable(wishlist);
    }

    public List<Wishlist> getWishlistsByUserId(String userId) {
        List<Wishlist> wishlists = null;
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":userId", new AttributeValue().withS(userId));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :userId")
                .withExpressionAttributeValues(eav);

        try {
            List<Wishlist> useResult = dynamoDBMapper.scan(Wishlist.class, scanExpression);
            if (!useResult.isEmpty()) {
                wishlists = useResult;
                System.out.println("Wishlists found: " + wishlists);
            } else {
                System.out.println("Wishlists not found with that user.");
            }
        } catch (Exception e) {
            System.err.println("Error during DynamoDB scan:");
            e.printStackTrace(); // Print stack trace for debugging
        }

        return wishlists;
    }

    public Optional<Wishlist> updateWishlist(String wishlistId, Wishlist updatedWishlist){
        Optional<Wishlist> wishlist = Optional.ofNullable(dynamoDBMapper.load(Wishlist.class, wishlistId));

        BeanUtils.copyProperties(updatedWishlist, wishlist );

        dynamoDBMapper.save(wishlist);

        return wishlist;
    }

    public Optional<Wishlist> addProductToWishlist(String wishlistId, String productId){
        Optional<Wishlist> wishlist = Optional.ofNullable(dynamoDBMapper.load(Wishlist.class, wishlistId));

        // Check if wishlist exists
        if (wishlist.isPresent()) {
            Wishlist currentWishlist = wishlist.get();

            // Assuming Wishlist has a List<String> or similar for productIds
            List<String> productIds = currentWishlist.getProducts();

            // Check if the product ID is already in the wishlist
            if (!productIds.contains(productId)) {
                // Add the product ID if not already present
                productIds.add(productId);

                Wishlist updatedWishlist = Wishlist.builder().wishlistId(currentWishlist.getWishlistId())
                        .name(currentWishlist.getName())
                        .products(productIds)
                        .userId(currentWishlist.getUserId()).build();

                // Save the updated wishlist
                dynamoDBMapper.save(updatedWishlist);

                return Optional.of(updatedWishlist);
            }else{
                // Product not found in wishlist, return the original wishlist
                return Optional.empty(); // Optionally, return an empty Optional or the original wishlist
            }
        } else {
            // Product not found in wishlist, return the original wishlist
            return Optional.empty(); // Optionally, return an empty Optional or the original wishlist
        }
    }

    public Optional<Wishlist> removeProductFromWishlist( String wishlistId, String productId ) {
        Optional<Wishlist> wishlist = Optional.ofNullable(dynamoDBMapper.load(Wishlist.class, wishlistId));

        // Check if wishlist exists
        if (wishlist.isPresent()) {
            Wishlist currentWishlist = wishlist.get();

            // Assuming Wishlist has a List<String> or similar for productIds
            List<String> productIds = currentWishlist.getProducts();

            // Check if the product ID exists in the wishlist
            if (productIds.contains(productId)) {
                // Remove the product ID from the wishlist
                productIds.remove(productId);

                // Rebuild the wishlist object with the updated list of products
                Wishlist updatedWishlist = Wishlist.builder()
                        .wishlistId(currentWishlist.getWishlistId())
                        .name(currentWishlist.getName())
                        .products(productIds)
                        .userId(currentWishlist.getUserId())
                        .build();

                // Save the updated wishlist
                dynamoDBMapper.save(updatedWishlist);

                // Return the updated wishlist
                return Optional.of(updatedWishlist);
            } else {
                // Product not found in wishlist, return the original wishlist
                return Optional.empty(); // Optionally, return an empty Optional or the original wishlist
            }
        } else {
            // Wishlist does not exist
            return Optional.empty();
        }
    }

    public void deleteWishlist(String wishlistId) {
        Optional<Wishlist> wishlist = Optional.ofNullable(dynamoDBMapper.load(Wishlist.class, wishlistId));

        if(wishlist.isPresent()) {
            dynamoDBMapper.delete(wishlist);
        }

    }
}
