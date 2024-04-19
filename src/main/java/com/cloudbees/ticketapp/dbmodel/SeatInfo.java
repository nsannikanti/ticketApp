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
@Table(name = "seat_info")
public class SeatInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long Id;

    @Column(name = "section")
    private String section;

    @Column(name = "seat_no")
    private String seatNo;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    private String status;

}


