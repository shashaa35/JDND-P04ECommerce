package com.example.demo.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;

public class CartControllerTest {

    private CartController cartController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp()
    {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void verify_addToCart()
    {
        Item item = new Item();
        item.setId(0L);
        item.setName("Round Widget");
        item.setDescription("A widget that is round");
        item.setPrice(BigDecimal.valueOf(3));
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item));

        Cart cart = new Cart();
        User user = new User();
        user.setId(0L);
        user.setUsername("test");
        user.setPassword("helloWorldCrack it");
        user.setCart(cart);
        when(userRepository.findByUsername("test")).thenReturn(user);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(0L);
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setQuantity(4);

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(12,response.getBody().getTotal().intValue());
    }

    @Test
    public void negative_addToCart()
    {

        Item item = new Item();
        item.setId(0L);
        item.setName("Round Widget");
        item.setDescription("A widget that is round");
        item.setPrice(BigDecimal.valueOf(3));
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item));

        Cart cart = new Cart();
        User user = new User();
        user.setId(0L);
        user.setUsername("test");
        user.setPassword("helloWorldCrack it");
        user.setCart(cart);
        when(userRepository.findByUsername("test")).thenReturn(user);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setQuantity(4);

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);

        assertEquals(404, response.getStatusCodeValue());
    }
}