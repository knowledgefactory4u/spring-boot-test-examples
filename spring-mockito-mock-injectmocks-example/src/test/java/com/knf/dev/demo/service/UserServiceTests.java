package com.knf.dev.demo.service;

import com.knf.dev.demo.entity.User;
import com.knf.dev.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testFindAllUsers() {

        List<User> list = new ArrayList<>();
        User userOne = new User(1l,"John", "John","john@hmail.com");
        User userTwo = new User(2l,"Alpha", "Alpha","alpha@hmail.com");
        User userThree = new User(3l,"Beta", "Beta","beta@hmail.com");

        list.add(userOne);
        list.add(userTwo);
        list.add(userThree);

        when(userRepository.findAll()).thenReturn(list);

        List<User> allUsers = userService.getAllUsers();

        assertEquals(3, allUsers.size());
    }

    @Test
    void testCreateUser() {

        User user = new User(1l,"John", "John","john@hmail.com");

        when(userRepository.save(user)).thenReturn(user);

        User user1 = userService.createUser(user);

        assertEquals("john@hmail.com", user1.getEmail());
    }

    @Test
    void testfindUserById() {

        User user = new User(1l,"John", "John","john@hmail.com");

        when(userRepository.findById(1l)).thenReturn(Optional.of(user));

        User user1 = userService.getUserById(1l);

        assertEquals("john@hmail.com", user1.getEmail());
    }
}
