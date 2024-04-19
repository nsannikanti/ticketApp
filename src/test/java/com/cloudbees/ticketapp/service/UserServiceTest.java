package com.cloudbees.ticketapp.service;

import com.cloudbees.ticketapp.datamodel.PurchaseTicketModel;
import com.cloudbees.ticketapp.datamodel.UserModel;
import com.cloudbees.ticketapp.dbmodel.SeatInfo;
import com.cloudbees.ticketapp.dbmodel.Ticket;
import com.cloudbees.ticketapp.dbmodel.User;
import com.cloudbees.ticketapp.repository.SeatInfoRepository;
import com.cloudbees.ticketapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SeatInfoRepository seatInfoRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getUsersBySection_Success() {
        String section = "A";
        List<User> users = new ArrayList<>();

        when(userRepository.findAll()).thenReturn(users);
        when(seatInfoRepository.findBySeatNoAndSection(anyString(), eq(section))).thenReturn(mock(SeatInfo.class));
        List<UserModel> usersBySection = userService.getUsersBySection(section);
        assertNotNull(usersBySection);
    }

    @Test
    void removeUserFromTrain_UserExists_Success() {
        // Arrange
        Long userId = 1L;
        User user = Mockito.mock(User.class); // Mock User object
        Ticket ticket = Mockito.mock(Ticket.class); // Mock Ticket object
        when(userRepository.findById(userId)).thenReturn(Optional.of(user)); // Stubbing findById method
        when(user.getTicket()).thenReturn(ticket); // Stubbing getTicket method

        // Act
        boolean result = userService.removeUserFromTrain(userId);

        // Assert
        assertTrue(result);
    }

    @Test
    void removeUserFromTrain_UserNotFound_Failure() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        boolean result = userService.removeUserFromTrain(userId);
        assertFalse(result);
    }

    private List<SeatInfo> createSeatInfoList() {
        return new ArrayList<>();
    }
}

