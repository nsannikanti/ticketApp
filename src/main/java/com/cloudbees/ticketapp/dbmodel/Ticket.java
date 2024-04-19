package com.cloudbees.ticketapp.dbmodel;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@NoArgsConstructor
@Data
@Table(name = "ticket_details")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(name = "departure_from")
    private String departureFrom;

    @Column(name = "destination_to")
    private String destinationTo;

    @Column(name = "price_paid")
    private BigDecimal pricePaid;

    @Column(name = "seat_no")
    private String seatNo;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    private User user;
}

