package com.example.prography_quest;

import com.example.prography_quest.domain.init.dto.InitRequest;
import com.example.prography_quest.domain.room.domain.Room;
import com.example.prography_quest.domain.room.domain.Room_type;
import com.example.prography_quest.domain.room.domain.Status;
import com.example.prography_quest.domain.room.dto.CreateRoomRequest;
import com.example.prography_quest.domain.room.dto.JoinOutRoomRequest;
import com.example.prography_quest.domain.room.repository.RoomRepository;
import com.example.prography_quest.domain.room.service.RoomService;
import com.example.prography_quest.domain.userRoom.domain.Team;
import com.example.prography_quest.domain.userRoom.domain.UserRoom;
import com.example.prography_quest.domain.userRoom.dto.ChangeTeamRequest;
import com.example.prography_quest.domain.userRoom.repository.UserRoomRepository;
import com.example.prography_quest.global.common.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

//@SpringBootTest
//@AutoConfigureMockMvc
//public class TestCase3 {
//    @Autowired
//    protected MockMvc mockMvc;
//
//    @Autowired
//    protected ObjectMapper objectMapper;
//
//    @Autowired
//    private UserRoomRepository userRoomRepository;
//
//    @Autowired
//    private RoomService roomService;
//
//    @Autowired
//    protected RoomRepository roomRepository;

//    @DisplayName("TC3 테스트 성공")
//    @Test
//    public void testTC3() throws Exception {
//        String url = "/init";
//        ApiResponse<Void> response = new ApiResponse<Void>().ok();
//        InitRequest request = InitRequest.builder()
//                .seed(40)
//                .quantity(10)
//                .build();
//
//        ResultActions resultActions = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(request)));
//
//        url = "/room";
//        CreateRoomRequest createRoomRequest = CreateRoomRequest.builder()
//                .roomType(Room_type.DOUBLE)
//                .userId(1)
//                .title("a")
//                .build();
//
//        resultActions = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(createRoomRequest)));
//
//        List<UserRoom> list = userRoomRepository.findAll();
//        assertThat(list.size()).isEqualTo(1);
//        List<Room> dlist = roomRepository.findAll();
//        assertThat(dlist.size()).isEqualTo(1);
//
//        url = "/room/attention/1";
//        JoinOutRoomRequest joinOutRoomRequest = JoinOutRoomRequest.builder()
//                .userId(2)
//                .build();
//
//        resultActions = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(joinOutRoomRequest)));
//
//        joinOutRoomRequest = JoinOutRoomRequest.builder()
//                .userId(3)
//                .build();
//
//        resultActions = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(joinOutRoomRequest)));
//
//        HashMap<Team, Integer> map = new HashMap<>();
//        list = userRoomRepository.findAll();
//        for(UserRoom userRoom : list)
//            map.put(userRoom.getTeam(), map.getOrDefault(userRoom.getTeam(), 0) + 1);
//
//        assertThat(map.get(Team.RED)).isEqualTo(2);
//        assertThat(map.get(Team.BLUE)).isEqualTo(1);
//
//        url = "/team/1";
//        ChangeTeamRequest changeTeamRequest = ChangeTeamRequest.builder()
//                .userId(2)
//                .build();
//
//        resultActions = mockMvc.perform(put(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(changeTeamRequest)));
//
//        map = new HashMap<>();
//        list = userRoomRepository.findAll();
//        for(UserRoom userRoom : list)
//            map.put(userRoom.getTeam(), map.getOrDefault(userRoom.getTeam(), 0) + 1);
//
//        assertThat(map.get(Team.RED)).isEqualTo(1);
//        assertThat(map.get(Team.BLUE)).isEqualTo(2);
//
//
//        joinOutRoomRequest = JoinOutRoomRequest.builder()
//                .userId(4)
//                .build();
//
//        resultActions = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(joinOutRoomRequest)));
//
//        url = "/room/out/1";
//        JoinOutRoomRequest joinOutRoomRequest1 = JoinOutRoomRequest.builder()
//                .userId(3)
//                .build();
//
//        resultActions = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(joinOutRoomRequest1)));
//
//        map = new HashMap<>();
//        list = userRoomRepository.findAll();
//        for(UserRoom userRoom : list)
//            map.put(userRoom.getTeam(), map.getOrDefault(userRoom.getTeam(), 0) + 1);
//
//        assertThat(map.get(Team.RED)).isEqualTo(1);
//        assertThat(map.get(Team.BLUE)).isEqualTo(1);
//
//        url = "/room/attention/1";
//        joinOutRoomRequest = JoinOutRoomRequest.builder()
//                .userId(5)
//                .build();
//
//        resultActions = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(joinOutRoomRequest)));
//
//        joinOutRoomRequest = JoinOutRoomRequest.builder()
//                .userId(6)
//                .build();
//
//        resultActions = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(joinOutRoomRequest)));
//
//        map = new HashMap<>();
//        list = userRoomRepository.findAll();
//        for(UserRoom userRoom : list)
//            map.put(userRoom.getTeam(), map.getOrDefault(userRoom.getTeam(), 0) + 1);
//
//        assertThat(map.get(Team.RED)).isEqualTo(2);
//        assertThat(map.get(Team.BLUE)).isEqualTo(2);
//
//        url = "/room/out/1";
//        joinOutRoomRequest1 = JoinOutRoomRequest.builder()
//                .userId(1)
//                .build();
//
//        resultActions = mockMvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(joinOutRoomRequest1)));
//
//        map = new HashMap<>();
//        list = userRoomRepository.findAll();
//
//        assertThat(list.size()).isEqualTo(0);
//        Room room = roomService.findRoom(1);
//        assertThat(room.getStatus()).isEqualTo(Status.FINISH);
//    }
//}
