package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class UserControllerTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testCreateUser() throws Exception {

        BDDMockito.given(bCryptPasswordEncoder.encode("password")).willReturn("secretpassword");

        ResponseEntity<User> responseEntity = userController.createUser(
                SareetaApplicationTestUtil.getMockUserRequest());

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getPassword(), "secretpassword");
        assertEquals(responseEntity.getBody().getUsername(), "udacity");

    }

    @Test
    public void testCreateUserInvalidRequest() throws Exception {

        BDDMockito.given(bCryptPasswordEncoder.encode("password")).willReturn("secretpassword");

        ResponseEntity<User> responseEntity = userController.createUser(
                SareetaApplicationTestUtil.getMockUserRequestInvalid());

        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);

    }

    @Test
    public void testGetUserByName() throws Exception {

        BDDMockito.given(userRepository.findByUsername("udacity"))
                .willReturn(SareetaApplicationTestUtil.getMockUser());

        ResponseEntity<User> responseEntity = userController.findByUserName("udacity");

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getUsername(), "udacity");
        assertEquals(responseEntity.getBody().getId(), 1);

    }

    @Test
    public void testGetUserByNameNotFound() throws Exception {

        BDDMockito.given(userRepository.findByUsername("udacity"))
                .willReturn(SareetaApplicationTestUtil.getMockUser());

        ResponseEntity<User> responseEntity = userController.findByUserName("username");

        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void testGetUserById() throws Exception {

        BDDMockito.given(userRepository.findById(Long.valueOf(1)))
                .willReturn(Optional.of(SareetaApplicationTestUtil.getMockUser()));

        ResponseEntity<User> responseEntity = userController.findById(Long.valueOf(1));

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getUsername(), "udacity");

    }

}
