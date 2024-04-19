package com.cloudbees.ticketapp.datamodel;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PurchaseTicketModel {
    private String departureFrom;
    private String destinationTo;
    private UserModel user;
    private BigDecimal pricePaid;
    private String seat;
}
