package com.example.prography_quest.domain.room.controller;

import com.example.prography_quest.domain.room.domain.Room;
import com.example.prography_quest.domain.room.domain.Room_type;
import com.example.prography_quest.domain.room.dto.CreateRoomRequest;
import com.example.prography_quest.domain.room.dto.JoinOutRoomRequest;
import com.example.prography_quest.domain.room.dto.RoomResponse;
import com.example.prography_quest.domain.room.dto.UpdateRoomRequest;
import com.example.prography_quest.domain.room.service.RoomService;
import com.example.prography_quest.domain.user.domain.Status;
import com.example.prography_quest.domain.user.domain.User;
import com.example.prography_quest.domain.user.dto.UserResponse;
import com.example.prography_quest.domain.user.service.UserService;
import com.example.prography_quest.domain.userRoom.domain.Team;
import com.example.prography_quest.domain.userRoom.domain.UserRoom;
import com.example.prography_quest.domain.userRoom.service.UserRoomService;
import com.example.prography_quest.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Tag(name = "Room", description = "room 관련 작업 API")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final UserService userService;
    private final UserRoomService userRoomService;

    @CrossOrigin
    @Operation(summary = "방 생성 api")
    @PostMapping("/room")
    public ResponseEntity<ApiResponse> createRoom(@RequestBody CreateRoomRequest createRoomRequest) {
        User user = userService.findUser(createRoomRequest.getUserId());

        if (!user.getStatus().equals(Status.ACTIVE))
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail());
        if (!userRoomService.findAllUserRoom(user).isEmpty())
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail());

        Room room = Room.builder()
                .title(createRoomRequest.getTitle())
                .room_type(createRoomRequest.getRoomType())
                .user(user)
                .status(com.example.prography_quest.domain.room.domain.Status.WAIT)
                .build();
        roomService.saveRoom(room);
        userRoomService.saveUserRoom(UserRoom.builder()
                .user(user)
                .room(room)
                .team(Team.RED)
                .build());
        return ResponseEntity.ok().body(new ApiResponse<Void>().ok());
    }

    @CrossOrigin
    @Operation(summary = "저장된 room들 지정된 만큼 반환 api")
    @GetMapping("/room")
    public ResponseEntity<ApiResponse> getRooms(
            @Positive @RequestParam(name = "size") int size,
            @Positive @RequestParam(name = "page") int page) {

        Page<Room> roomPage = roomService.findRooms(page, size);
        List<Room> list = roomPage.getContent();
        List<RoomResponse.Data> dlist = new ArrayList<>();
        for (Room room : list) {
            dlist.add(RoomResponse.Data.builder()
                    .id(room.getId())
                    .title(room.getTitle())
                    .hostId(room.getHostId())
                    .roomType(room.getRoomType())
                    .status(room.getStatus())
                    .build());
        }

        RoomResponse roomResponse = RoomResponse.builder()
                .totalElements((int) roomPage.getTotalElements())
                .totalPages((int) roomPage.getTotalPages())
                .roomList(dlist)
                .build();
        return ResponseEntity.ok().body(new ApiResponse<RoomResponse>(roomResponse).ok());
    }

    @CrossOrigin
    @Operation(summary = "저장된 room들 지정된 만큼 반환 api")
    @GetMapping("/room/{roomId}")
    public ResponseEntity<ApiResponse> getRoom(@PathVariable int roomId) {
        return ResponseEntity.ok().body(new ApiResponse<Room>(roomService.findRoom(roomId)).ok());
    }

    @CrossOrigin
    @Operation(summary = "방 참가 api")
    @PostMapping("/room/attention/{roomId}")
    public ResponseEntity<ApiResponse> joinRoom(@PathVariable int roomId, @RequestBody JoinOutRoomRequest joinRoomRequest) {
        HashMap<Team, Integer> map = new HashMap<>();
        Room room;
        User user;
        int num = -1;

        try {
            room = roomService.findRoom(roomId);
            user = userService.findUser(joinRoomRequest.getUserId());
            if (room.getRoomType().equals(Room_type.DOUBLE)) num = 4;
            else if (room.getRoomType().equals(Room_type.SINGLE)) num = 2;
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail());
        }

        List<UserRoom> list = userRoomService.findAllUserRoom(room);
        if (!room.getStatus().equals(com.example.prography_quest.domain.room.domain.Status.WAIT))
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail());
        if (!user.getStatus().equals(Status.ACTIVE))
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail());
        if (!userRoomService.findAllUserRoom(user).isEmpty())
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail());
        if (list.size() >= num)
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail());

        for (UserRoom userRoom : list)
            map.put(userRoom.getTeam(), map.getOrDefault(userRoom.getTeam(), 0) + 1);

        if (map.get(Team.RED) < num / 2) {
            userRoomService.saveUserRoom(UserRoom.builder()
                    .user(user)
                    .room(room)
                    .team(Team.RED)
                    .build());
        } else if (map.get(Team.RED) == num / 2) {
            userRoomService.saveUserRoom(UserRoom.builder()
                    .user(user)
                    .room(room)
                    .team(Team.BLUE)
                    .build());
        }
        return ResponseEntity.ok().body(new ApiResponse<Void>().ok());
    }

    @CrossOrigin
    @Operation(summary = "방 나가기 api")
    @PostMapping("/room/out/{roomId}")
    public ResponseEntity<ApiResponse> outRoom(@PathVariable int roomId, @RequestBody JoinOutRoomRequest outRoomRequest) {
        List<UserRoom> list;
        Room room;

        try {
            list = userRoomService.findAllUserRoom(userService.findUser(outRoomRequest.getUserId()));
            room = roomService.findRoom(roomId);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail());
        }

        if (list.isEmpty())
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail());
        if (!room.getStatus().equals(com.example.prography_quest.domain.room.domain.Status.WAIT))
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail());

        if (room.getHostId() == outRoomRequest.getUserId()) {
            list = userRoomService.findAllUserRoom(room);
            for (UserRoom userRoom : list)
                userRoomService.deleteUserRoom(userRoom.getId());
            roomService.updateRoom(UpdateRoomRequest.builder()
                    .status(com.example.prography_quest.domain.room.domain.Status.FINISH)
                    .build(), room);
            return ResponseEntity.ok().body(new ApiResponse<Void>().ok());
        }
        userRoomService.deleteUserRoom(outRoomRequest.getUserId());
        return ResponseEntity.ok().body(new ApiResponse<Void>().ok());
    }


    @CrossOrigin
    @Operation(summary = "게임시작 api")
    @PutMapping("/room/start/{roomId}")
    public ResponseEntity<ApiResponse> startRoom(@PathVariable int roomId, @RequestBody JoinOutRoomRequest startRoomRequest) {
        HashMap<Team, Integer> map = new HashMap<>();
        Room room;
        User user;
        int num = -1;

        try {
            room = roomService.findRoom(roomId);
            user = userService.findUser(startRoomRequest.getUserId());
            if (room.getRoomType().equals(Room_type.DOUBLE))
                num = 4;
            else if (room.getRoomType().equals(Room_type.SINGLE))
                num = 2;
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail());
        }

        List<UserRoom> list = userRoomService.findAllUserRoom(room);
        if (user.getId() != room.getHostId())
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail());
        if (list.size() != num)
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail());
        if (!room.getStatus().equals(com.example.prography_quest.domain.room.domain.Status.WAIT))
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail());

        new thread(roomService, room).start();
        return ResponseEntity.ok().body(new ApiResponse<Void>().ok());
    }
}

@RequiredArgsConstructor
class thread extends Thread {
    private RoomService roomService;
    private Room room;

    public thread(RoomService roomService, Room room) {
        this.roomService = roomService;
        this.room = room;
    }

    public void run() {
        try {
            roomService.updateRoom(UpdateRoomRequest.builder()
                    .status(com.example.prography_quest.domain.room.domain.Status.PROGRESS)
                    .build(), room);
            Thread.sleep(60000);
            roomService.updateRoom(UpdateRoomRequest.builder()
                    .status(com.example.prography_quest.domain.room.domain.Status.FINISH)
                    .build(), room);
        } catch (Exception e) {

        }
    }
}


