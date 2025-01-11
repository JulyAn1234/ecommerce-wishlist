package com.ecommerceWishlist.ecommerceWishlist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HealthCheckTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void healtCheckTest() throws Exception{
        this.mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Wishlist Service Working Properly"));
    }

}
