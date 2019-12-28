package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.demo.util.SareetaApplicationTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class OrderControllerTest {

    @Autowired
    private OrderController orderController;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void testSubmitOrder() {

        User mockUser = getMockUser();
        Cart mockCart = getMockCart();

        mockUser.setCart(mockCart);

        BDDMockito.given(userRepository.findByUsername("udacity")).willReturn(mockUser);

        ResponseEntity<UserOrder> response = orderController.submit("udacity");

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getUser().getUsername(), "udacity");
        assertEquals(response.getBody().getItems().size(), 1);

    }

    @Test
    public void testSubmitOrderUserNotFound() {

        User mockUser = getMockUser();
        Cart mockCart = getMockCart();

        mockUser.setCart(mockCart);

        BDDMockito.given(userRepository.findByUsername("realuser")).willReturn(mockUser);

        ResponseEntity<UserOrder> response = orderController.submit("udacity");

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void testHistoryOrders() {

        User mockUser = getMockUser();

        BDDMockito.given(userRepository.findByUsername("udacity")).willReturn(mockUser);
        BDDMockito.given(orderRepository.findByUser(any())).willReturn(Collections.singletonList(getUserOrders()));
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("udacity");

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().size(), 1);
        assertEquals(response.getBody().get(0).getUser().getUsername(), "udacity");
        assertEquals(response.getBody().get(0).getTotal(), BigDecimal.valueOf(123.45));
    }

    @Test
    public void testHistoryOrdersUserNotFound() {

        User mockUser = getMockUser();

        BDDMockito.given(userRepository.findByUsername("realuser")).willReturn(mockUser);
        BDDMockito.given(orderRepository.findByUser(any())).willReturn(Collections.singletonList(getUserOrders()));
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("udacity");

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

}
