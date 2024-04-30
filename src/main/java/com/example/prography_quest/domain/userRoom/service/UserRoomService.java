package com.example.prography_quest.domain.userRoom.service;

import com.example.prography_quest.domain.room.domain.Room;
import com.example.prography_quest.domain.room.dto.UpdateRoomRequest;
import com.example.prography_quest.domain.user.domain.User;
import com.example.prography_quest.domain.userRoom.domain.UserRoom;
import com.example.prography_quest.domain.userRoom.dto.UpdateTeamRequest;
import com.example.prography_quest.domain.userRoom.repository.UserRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserRoomService {
    private final UserRoomRepository userRoomRepository;

    public void deleteAllUserRoom() {
        userRoomRepository.deleteAll();
    }

    public void deleteUserRoom(int id) {
        userRoomRepository.deleteById(id);
    }

    public void saveUserRoom(UserRoom userRoom) {
        userRoomRepository.save(userRoom);
    }

    @Transactional
    public void updateTeam(UpdateTeamRequest request, UserRoom userRoom) {
        userRoomRepository.save(userRoom.updateTeam(request));
    }

    public List<UserRoom> findAllUserRoom(User user) {
        List<UserRoom> list = userRoomRepository.findAll();
        List<UserRoom> dlist = new ArrayList<>();
        for (UserRoom userRoom : list)
            if (Objects.equals(userRoom.getUser_id(), user.getId()))
                dlist.add(userRoom);
        return dlist;
    }

    public List<UserRoom> findAllUserRoom(Room room) {
        List<UserRoom> list = userRoomRepository.findAll();
        List<UserRoom> dlist = new ArrayList<>();
        for (UserRoom userRoom : list)
            if (Objects.equals(userRoom.getRoom_id(), room.getId()))
                dlist.add(userRoom);
        return dlist;
    }
}
