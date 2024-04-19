package com.cloudbees.ticketapp.repository;

import com.cloudbees.ticketapp.dbmodel.SeatInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatInfoRepository extends JpaRepository<SeatInfo, Long> {
    @Query(value = "SELECT * FROM seat_info WHERE status = 'Available'", nativeQuery = true)
    List<SeatInfo> findAvailableSeats();

    @Transactional
    @Modifying
    @Query("UPDATE SeatInfo s SET s.status = :status WHERE s.seatNo = :seatNo")
    void updateSeatStatus(String seatNo, String status);
    SeatInfo findBySeatNoAndSection(String seatNo, String section);
}
