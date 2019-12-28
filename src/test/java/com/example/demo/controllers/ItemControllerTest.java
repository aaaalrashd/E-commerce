package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.util.SareetaApplicationTestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ItemControllerTest {

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private ItemController itemController;


    @Test
    public void testGetItemsByName() {
        BDDMockito.given(itemRepository.findByName("Mock Item"))
                .willReturn(Collections.singletonList(SareetaApplicationTestUtil.getMockItem()));

        ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("Mock Item");

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().size(), 1);
        assertEquals(responseEntity.getBody().get(0).getName(), "Mock Item");
        assertEquals(responseEntity.getBody().get(0).getPrice(), BigDecimal.valueOf(123.45));
    }

    @Test
    public void testGetItemsByNameNotFound() {
        BDDMockito.given(itemRepository.findByName("Mock Item"))
                .willReturn(Collections.singletonList(SareetaApplicationTestUtil.getMockItem()));

        ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("Real Item");

        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetItemsById() {
        BDDMockito.given(itemRepository.findById(Long.valueOf(1)))
                .willReturn(Optional.of(SareetaApplicationTestUtil.getMockItem()));

        ResponseEntity<Item> responseEntity = itemController.getItemById(Long.valueOf(1));

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getName(), "Mock Item");
        assertEquals(responseEntity.getBody().getPrice(), BigDecimal.valueOf(123.45));
        assertEquals(responseEntity.getBody().getId(), Long.valueOf(1));
    }

    @Test
    public void testGetItemsByIdNotFound() {
        BDDMockito.given(itemRepository.findById(Long.valueOf(1)))
                .willReturn(Optional.of(SareetaApplicationTestUtil.getMockItem()));

        ResponseEntity<Item> responseEntity = itemController.getItemById(Long.valueOf(2));

        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetItems() {
        BDDMockito.given(itemRepository.findAll())
                .willReturn(Collections.singletonList(SareetaApplicationTestUtil.getMockItem()));

        ResponseEntity<List<Item>> responseEntity = itemController.getItems();

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().size(), 1);
        assertEquals(responseEntity.getBody().get(0).getName(), "Mock Item");
        assertEquals(responseEntity.getBody().get(0).getPrice(), BigDecimal.valueOf(123.45));
    }


}
