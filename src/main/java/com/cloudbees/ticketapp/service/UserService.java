package com.cloudbees.ticketapp.service;

import com.cloudbees.ticketapp.datamodel.PurchaseTicketModel;
import com.cloudbees.ticketapp.datamodel.UserModel;
import com.cloudbees.ticketapp.dbmodel.SeatInfo;
import com.cloudbees.ticketapp.dbmodel.User;
import com.cloudbees.ticketapp.dbmodel.Ticket;
import com.cloudbees.ticketapp.repository.SeatInfoRepository;
import com.cloudbees.ticketapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private static final String STATUS_ALLOTTED = "Allotted";
    private static final String STATUS_AVAILABLE = "Available";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeatInfoRepository seatInfoRepository;

    public UserModel purchaseTicket(PurchaseTicketModel purchaseTicketModel) {
        User user = createUserFromModel(purchaseTicketModel);
        Ticket ticket = createTicketFromModel(purchaseTicketModel);
        assignSeatAndPrice(ticket);

        // Establish one-to-one relationship between User and Ticket
        user.setTicket(ticket);
        ticket.setUser(user);

        User savedUser = userRepository.save(user);
        updateSeatStatus(savedUser);
        return mapUserToUserModel(savedUser);
    }

    private void updateSeatStatus(User savedUser) {
        savedUser.getTicket().getTicketId();
        String seatNo = savedUser.getTicket().getSeatNo();

        seatInfoRepository.updateSeatStatus(seatNo, STATUS_ALLOTTED);
    }

    private User createUserFromModel(PurchaseTicketModel purchaseTicketModel) {
        User user = new User();
        user.setFirstName(purchaseTicketModel.getUser().getFirstName());
        user.setLastName(purchaseTicketModel.getUser().getLastName());
        user.setEmail(purchaseTicketModel.getUser().getEmail());
        return user;
    }

    private Ticket createTicketFromModel(PurchaseTicketModel purchaseTicketModel) {
        Ticket ticket = new Ticket();
        ticket.setDepartureFrom(purchaseTicketModel.getDepartureFrom());
        ticket.setDestinationTo(purchaseTicketModel.getDestinationTo());
        return ticket;
    }

    private void assignSeatAndPrice(Ticket ticket) {
        List<SeatInfo> seatInfoList = seatInfoRepository.findAvailableSeats();
        if (!seatInfoList.isEmpty()) {
            SeatInfo firstAvailableSeat = seatInfoList.get(0); // Get the first available seat
            ticket.setSeatNo(firstAvailableSeat.getSeatNo());
            ticket.setPricePaid(firstAvailableSeat.getPrice());
        }

    }

    private UserModel mapUserToUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userModel.setEmail(user.getEmail());
        userModel.setPrice(user.getTicket().getPricePaid());
        userModel.setSeatNo(user.getTicket().getSeatNo());
        userModel.setDepartureFrom(user.getTicket().getDepartureFrom());
        userModel.setDestinationTo(user.getTicket().getDestinationTo());
        userModel.setUserId(user.getUserId());
        return userModel;
    }

    public List<UserModel> getUsersBySection(String section) {
        List<User> users = userRepository.findAll();
        List<UserModel> usersInSection = new ArrayList<>();

        for (User user : users) {
            Ticket ticket = user.getTicket();
            if (ticket != null) {
                String seatNo = ticket.getSeatNo();
                SeatInfo seatInfo = seatInfoRepository.findBySeatNoAndSection(seatNo, section);
                if (seatInfo != null) {
                    UserModel userModel = new UserModel();
                    userModel.setFirstName(user.getFirstName());
                    userModel.setLastName(user.getLastName());
                    userModel.setEmail(user.getEmail());
                    userModel.setSeatNo(seatNo);
                    userModel.setPrice(user.getTicket().getPricePaid());
                    userModel.setDepartureFrom(user.getTicket().getDepartureFrom());
                    userModel.setDestinationTo(user.getTicket().getDestinationTo());
                    userModel.setUserId(user.getUserId());
                    usersInSection.add(userModel);
                }
            }
        }

        return usersInSection;
    }

    public boolean removeUserFromTrain(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userRepository.delete(user);
            String seatNo = user.getTicket().getSeatNo();
            seatInfoRepository.updateSeatStatus(seatNo, STATUS_AVAILABLE);
            return true;
        } else {
            return false;
        }
    }

    public boolean modifyUserSeat(Long userId, String newSeat) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String oldSeat = user.getTicket().getSeatNo();
            user.getTicket().setSeatNo(newSeat);
            userRepository.save(user);
            seatInfoRepository.updateSeatStatus(user.getTicket().getSeatNo(), STATUS_ALLOTTED);
            seatInfoRepository.updateSeatStatus(oldSeat,STATUS_AVAILABLE);
            return true;
        } else {
            return false; // User not found
        }
    }
}
