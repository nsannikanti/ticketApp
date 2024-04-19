package com.cloudbees.ticketapp.controller;

import com.cloudbees.ticketapp.datamodel.PurchaseTicketModel;
import com.cloudbees.ticketapp.datamodel.UserModel;
import com.cloudbees.ticketapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void purchaseTicket_Success() {

        PurchaseTicketModel purchaseTicketModel = new PurchaseTicketModel();
        UserModel userModel = new UserModel();
        when(userService.purchaseTicket(any())).thenReturn(userModel);

        ResponseEntity<UserModel> responseEntity = userController.purchaseTicket(purchaseTicketModel);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(userModel, responseEntity.getBody());
    }

    @Test
    void getUsersBySection_Success() {

        String section = "A";
        UserModel userModel = new UserModel();
        when(userService.getUsersBySection(anyString())).thenReturn(Collections.singletonList(userModel));

        // Act
        ResponseEntity<List<UserModel>> responseEntity = userController.getUsersBySection(section);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Collections.singletonList(userModel), responseEntity.getBody());
    }

    @Test
    void modifyUserSeat_Success() {
        // Arrange
        Long userId = 1L;
        String newSeat = "B2";
        when(userService.modifyUserSeat(anyLong(), anyString())).thenReturn(true);

        // Act
        ResponseEntity<String> responseEntity = userController.modifyUserSeat(userId, newSeat);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User's seat modified successfully", responseEntity.getBody());
    }

    @Test
    void getUsersBySection_UsersFound_Success() {
        String section = "A";
        List<UserModel> users = Collections.singletonList(new UserModel()); // Create a list of UserModels with expected values

        when(userService.getUsersBySection(section)).thenReturn(users);

        ResponseEntity<List<UserModel>> response = userController.getUsersBySection(section);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void modifyUserSeat_UserNotFound() {
        // Arrange
        Long userId = 1L;
        String newSeat = "B2";
        when(userService.modifyUserSeat(anyLong(), anyString())).thenReturn(false);

        // Act
        ResponseEntity<String> responseEntity = userController.modifyUserSeat(userId, newSeat);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("User not found"));
    }

    @Test
    void removeUserFromTrain_Success() {
        // Arrange
        Long userId = 1L;
        when(userService.removeUserFromTrain(anyLong())).thenReturn(true);

        // Act
        ResponseEntity<String> responseEntity = userController.removeUserFromTrain(userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User removed successfully", responseEntity.getBody());
    }

    @Test
    void removeUserFromTrain_UserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userService.removeUserFromTrain(anyLong())).thenReturn(false);

        // Act
        ResponseEntity<String> responseEntity = userController.removeUserFromTrain(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("User not found"));
    }
}
