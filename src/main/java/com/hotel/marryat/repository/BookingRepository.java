package com.hotel.marryat.repository;

import com.hotel.marryat.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByStartDateAndEndDate(LocalDate startDate, LocalDate endDate);

    @Query("Select b from Booking b where b.room.id = :roomId and ((b.startDate between :startDate and :endDate) or (b.endDate between :startDate and :endDate) or " +
            "(b.startDate < :startDate and b.endDate > :endDate))")
    List<Booking> findByRoomWithinDateRange(@Param("roomId") Integer roomId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
