package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.util.SareetaApplicationTestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.demo.util.SareetaApplicationTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CartControllerTest {

    @Autowired
    private CartController cartController;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ItemRepository itemRepository;

    @Test
    public void testAddToCart() {

        BDDMockito.given(userRepository.findByUsername("udacity")).willReturn(getMockUser());
        BDDMockito.given(itemRepository.findById(Long.valueOf(1))).willReturn(Optional.of(getMockItem()));

        ModifyCartRequest cartRequest = getMockCartRequest();

        ResponseEntity<Cart> responseEntity = cartController.addTocart(cartRequest);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getItems().size(),2);
        assertEquals(responseEntity.getBody().getItems().get(0).getName(), "Mock Item");
    }

    @Test
    public void testAddToCartUserNotFound() {

        BDDMockito.given(userRepository.findByUsername("realuser")).willReturn(getMockUser());
        BDDMockito.given(itemRepository.findById(Long.valueOf(1))).willReturn(Optional.of(getMockItem()));

        ModifyCartRequest cartRequest = getMockCartRequest();

        ResponseEntity<Cart> responseEntity = cartController.addTocart(cartRequest);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testAddToCartItemNotFound() {

        BDDMockito.given(userRepository.findByUsername("udacity")).willReturn(getMockUser());
        BDDMockito.given(itemRepository.findById(Long.valueOf(100))).willReturn(Optional.of(getMockItem()));

        ModifyCartRequest cartRequest = getMockCartRequest();

        ResponseEntity<Cart> responseEntity = cartController.addTocart(cartRequest);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testRemoveFromCart() {

        BDDMockito.given(userRepository.findByUsername("udacity")).willReturn(getMockUser());
        BDDMockito.given(itemRepository.findById(Long.valueOf(1))).willReturn(Optional.of(getMockItem()));

        ModifyCartRequest cartRequest = getMockCartRequest();

        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(cartRequest);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getItems().size(),0);
        //assertEquals(responseEntity.getBody().getItems().get(0).getName(), "Mock Item");
    }

    @Test
    public void testRemoveFromCartUserNotFound() {

        BDDMockito.given(userRepository.findByUsername("realuser")).willReturn(getMockUser());
        BDDMockito.given(itemRepository.findById(Long.valueOf(1))).willReturn(Optional.of(getMockItem()));

        ModifyCartRequest cartRequest = getMockCartRequest();

        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(cartRequest);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testRemoveFromCartItemNotFound() {

        BDDMockito.given(userRepository.findByUsername("udacity")).willReturn(getMockUser());
        BDDMockito.given(itemRepository.findById(Long.valueOf(100))).willReturn(Optional.of(getMockItem()));

        ModifyCartRequest cartRequest = getMockCartRequest();

        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(cartRequest);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
