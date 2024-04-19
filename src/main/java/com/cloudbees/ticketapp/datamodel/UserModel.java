package com.cloudbees.ticketapp.datamodel;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class UserModel {
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal price;
    private String seatNo;
    private String departureFrom;
    private String destinationTo;
    private Long userId;
}
