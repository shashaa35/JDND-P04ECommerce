package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemControllerTest {

    private ItemController itemController;

    @Autowired
    private ItemRepository itemRepository;

    @Before
    public void setUp(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void verify_getItems(){
        ResponseEntity<List<Item>> res = itemController.getItems();
        assertEquals(200,res.getStatusCodeValue());
        assertEquals(2,res.getBody().size());
        assertEquals("Round Widget",res.getBody().get(0).getName());
    }

    @Test
    public void negative_getItemById(){
        ResponseEntity<Item> res = itemController.getItemById(3L);
        assertEquals(404,res.getStatusCodeValue());
    }

    @Test
    public void verify_getItemsByName(){
        ResponseEntity<List<Item>> res = itemController.getItemsByName("Round Widget");
        assertEquals(200,res.getStatusCodeValue());
        assertEquals("Round Widget",res.getBody().get(0).getName());
    }


}
