package com.example.demo.util;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;

import java.math.BigDecimal;
import java.util.Collections;

public class SareetaApplicationTestUtil {

    public static CreateUserRequest getMockUserRequest() {

        CreateUserRequest mockCreateUserRequest = new CreateUserRequest();

        mockCreateUserRequest.setConfirmPassword("password");
        mockCreateUserRequest.setPassword("password");
        mockCreateUserRequest.setUsername("udacity");
        return mockCreateUserRequest;
    }

    public static CreateUserRequest getMockUserRequestInvalid() {

        CreateUserRequest mockCreateUserRequest = new CreateUserRequest();

        mockCreateUserRequest.setConfirmPassword("password");
        mockCreateUserRequest.setUsername("udacity");
        return mockCreateUserRequest;
    }

    public static User getMockUser() {

        User mockUser = new User();
        Cart mockCart = new Cart();
        mockUser.setUsername("udacity");
        mockUser.setPassword("secretpassword");
        mockUser.setId(1);
        mockUser.setCart(mockCart);
        return mockUser;
    }

    public static Item getMockItem() {
        Item item = new Item();
        item.setDescription("Item Description");
        item.setId(Long.valueOf(1));
        item.setName("Mock Item");
        item.setPrice(BigDecimal.valueOf(123.45));

        return item;
    }

    public static Cart getMockCart() {
        Cart mockCart = new Cart();
        mockCart.setId(Long.valueOf(1));
        mockCart.setItems(Collections.singletonList(getMockItem()));
        mockCart.setTotal(BigDecimal.valueOf(123.45));
        mockCart.setUser(getMockUser());

        return mockCart;
    }

    public static ModifyCartRequest getMockCartRequest(){
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setItemId(Long.valueOf(1));
        cartRequest.setQuantity(2);
        cartRequest.setUsername("udacity");
        return cartRequest;
    }

    public static UserOrder getUserOrders() {
        UserOrder userOrder = new UserOrder();
        userOrder.setItems(Collections.singletonList(getMockItem()));
        userOrder.setTotal(BigDecimal.valueOf(123.45));
        userOrder.setUser(getMockUser());
        userOrder.setId(Long.valueOf(1));

        return userOrder;
    }
}
