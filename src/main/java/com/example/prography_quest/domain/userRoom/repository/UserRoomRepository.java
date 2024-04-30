package com.example.prography_quest.domain.userRoom.repository;

import com.example.prography_quest.domain.user.domain.User;
import com.example.prography_quest.domain.userRoom.domain.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {
}
