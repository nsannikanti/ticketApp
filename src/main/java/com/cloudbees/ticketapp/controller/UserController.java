package com.cloudbees.ticketapp.controller;

import com.cloudbees.ticketapp.datamodel.PurchaseTicketModel;
import com.cloudbees.ticketapp.datamodel.UserModel;
import com.cloudbees.ticketapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/ticket")
public class UserController {
    private static final String SECTION = "section";

    @Autowired
    private UserService userService;

    @PostMapping("/purchase")
    public ResponseEntity<UserModel> purchaseTicket(@RequestBody PurchaseTicketModel purchaseTicketModel) {
        UserModel userDetails = userService.purchaseTicket(purchaseTicketModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDetails);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getUsersBySection(@RequestParam(SECTION) String section) {
        List<UserModel> users = userService.getUsersBySection(section);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{userId}/seat")
    public ResponseEntity<String> modifyUserSeat(@PathVariable Long userId, @RequestParam("newSeat") String newSeat) {

        boolean modified = userService.modifyUserSeat(userId, newSeat);

        if (modified) {
            return ResponseEntity.ok("User's seat modified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> removeUserFromTrain(@PathVariable Long userId) {
        boolean removed = userService.removeUserFromTrain(userId);
        if (removed) {
            return ResponseEntity.ok("User removed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or already removed");
        }
    }
}

