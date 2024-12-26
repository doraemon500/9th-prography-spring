package com.example.prography_quest.domain.userRoom.controller;

import com.example.prography_quest.domain.room.domain.Room;
import com.example.prography_quest.domain.room.domain.RoomStatus;
import com.example.prography_quest.domain.room.domain.Room_type;
import com.example.prography_quest.domain.room.service.RoomService;
import com.example.prography_quest.domain.user.service.UserService;
import com.example.prography_quest.domain.userRoom.domain.Team;
import com.example.prography_quest.domain.userRoom.domain.UserRoom;
import com.example.prography_quest.domain.userRoom.dto.ChangeTeamRequest;
import com.example.prography_quest.domain.userRoom.dto.UpdateTeamRequest;
import com.example.prography_quest.domain.userRoom.service.UserRoomService;
import com.example.prography_quest.global.common.exception.ExceptionCode;
import com.example.prography_quest.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Tag(name = "UserRoom", description = "userroom 관련 작업 API")
@RequiredArgsConstructor
public class UserRoomController {

    private final RoomService roomService;
    private final UserService userService;
    private final UserRoomService userRoomService;

    @CrossOrigin
    @Operation(summary = "저장된 유저들 지정된 만큼 반환 api")
    @PutMapping("/team/{roomId}")
    public ResponseEntity<ApiResponse> changeTeam(@PathVariable int roomId, @RequestBody ChangeTeamRequest changeTeamRequest) {
        HashMap<Team, Integer> map = new HashMap<>();
        UserRoom userRoom = null;
        Room room;
        List<UserRoom> list;
        int num = -1;

        try {
            list = userRoomService.findAllUserRoom(roomService.findRoom(roomId));
            room = roomService.findRoom(roomId);
            if (room.getRoomType().equals(Room_type.DOUBLE)) num = 4;
            else if (room.getRoomType().equals(Room_type.SINGLE)) num = 2;
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail(ExceptionCode.FAIL));
        }
        for (int i = 0; i < list.size(); i++) {
            userRoom = list.get(i);
            if (userRoom.getUser_id() == changeTeamRequest.getUserId()) break;
            if (i == list.size() - 1)
                return ResponseEntity.ok().body(new ApiResponse<Void>().fail(ExceptionCode.FAIL));
        }

        for(UserRoom val : list)
            map.put(val.getTeam(), map.getOrDefault(val.getTeam(), 0) + 1);

        if (!room.getStatus().equals(RoomStatus.WAIT))
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail(ExceptionCode.FAIL));
        if (map.get(Team.RED) == num / 2 && userRoom.getTeam().equals(Team.BLUE))
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail(ExceptionCode.FAIL));
        else if (map.get(Team.BLUE) == num / 2 && userRoom.getTeam().equals(Team.RED))
            return ResponseEntity.ok().body(new ApiResponse<Void>().fail(ExceptionCode.FAIL));

        if (userRoom.getTeam().equals(Team.RED))
            userRoomService.updateTeam(UpdateTeamRequest.builder()
                    .team(Team.BLUE).build(), userRoom);
        else
            userRoomService.updateTeam(UpdateTeamRequest.builder()
                    .team(Team.RED).build(), userRoom);
        return ResponseEntity.ok().body(new ApiResponse<Void>().ok(ExceptionCode.OK));
    }
}
