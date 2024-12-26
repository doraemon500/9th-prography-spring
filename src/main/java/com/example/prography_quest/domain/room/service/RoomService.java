package com.example.prography_quest.domain.room.service;

import com.example.prography_quest.domain.room.domain.Room;
import com.example.prography_quest.domain.room.dto.UpdateRoomRequest;
import com.example.prography_quest.domain.room.repository.RoomRepository;
import com.example.prography_quest.domain.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public void deleteAllRoom() {
        roomRepository.deleteAll();
    }

    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    @Transactional
    public void updateRoom(UpdateRoomRequest request, Room room) {
        roomRepository.save(room.updateRoom(request));
    }

    public Room findRoom(int roomId) {
        return roomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException());
    }

    public Page<Room> findRooms(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return roomRepository.findAllByOrderById(pageRequest);
    }
}
