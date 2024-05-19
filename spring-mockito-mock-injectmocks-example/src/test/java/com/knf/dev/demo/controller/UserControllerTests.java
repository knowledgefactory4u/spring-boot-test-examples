package com.knf.dev.demo.controller;

import com.knf.dev.demo.entity.User;
import com.knf.dev.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testFindAllUsers() {

        List<User> list = new ArrayList<>();
        User userOne = new User(1l,"John", "John","john@hmail.com");
        User userTwo = new User(2l,"Alpha", "Alpha","alpha@hmail.com");
        User userThree = new User(3l,"Beta", "Beta","beta@hmail.com");

        list.add(userOne);
        list.add(userTwo);
        list.add(userThree);

        when(userService.getAllUsers()).thenReturn(list);

        ResponseEntity<List<User>> users = userController.getAllUsers();

        assertEquals(3, users.getBody().size());
    }

    @Test
    void testCreateUser() {

        User user = new User(1l,"John", "John","john@hmail.com");

        when(userService.createUser(user)).thenReturn(user);

        ResponseEntity<User> user1 = userController.createUser(user);

        assertEquals("john@hmail.com", user1.getBody().getEmail());
    }

    @Test
    void testfindUserById() {

        User user = new User(1l,"John", "John","john@hmail.com");

        when(userService.getUserById(1l)).thenReturn(user);

        ResponseEntity<User> user1 = userController.getUserById(1l);

        assertEquals("john@hmail.com", user1.getBody().getEmail());
    }
}
