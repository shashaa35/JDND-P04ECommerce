package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;

    private UserRepository userRepository = mock(UserRepository.class);

    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp(){
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void verify_submit(){
        Item item = new Item();
        item.setId(0L);
        item.setName("Round Widget");
        item.setDescription("A widget that is round");
        item.setPrice(BigDecimal.valueOf(3));

        Cart cart = new Cart();
        cart.addItem(item);
        User user = new User();
        user.setId(0L);
        user.setUsername("test");
        user.setPassword("helloWorldCrack it");
        user.setCart(cart);

        when(userRepository.findByUsername("test")).thenReturn(user);
        ResponseEntity<UserOrder> res = orderController.submit("test");
        assertEquals(200,res.getStatusCodeValue());
        assertEquals(3,res.getBody().getTotal().intValue());
    }

    @Test
    public void negative_submit(){
        Item item = new Item();
        item.setId(0L);
        item.setName("Round Widget");
        item.setDescription("A widget that is round");
        item.setPrice(BigDecimal.valueOf(3));

        Cart cart = new Cart();
        cart.addItem(item);
        User user = new User();
        user.setId(0L);
        user.setUsername("test");
        user.setPassword("helloWorldCrack it");
        user.setCart(cart);

        when(userRepository.findByUsername("test")).thenReturn(user);
        ResponseEntity<UserOrder> res = orderController.submit("test1");
        assertEquals(404,res.getStatusCodeValue());
    }

}
