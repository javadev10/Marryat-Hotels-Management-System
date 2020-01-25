package com.hotel.marryat.repository;

import com.hotel.marryat.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
