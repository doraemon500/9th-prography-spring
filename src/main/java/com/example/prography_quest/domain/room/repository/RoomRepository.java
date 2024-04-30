package com.example.prography_quest.domain.room.repository;


import com.example.prography_quest.domain.room.domain.Room;
import com.example.prography_quest.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Page<Room> findAllByOrderById(Pageable pageable);
}
