package com.example.prography_quest;

import com.example.prography_quest.domain.init.dto.InitRequest;
import com.example.prography_quest.domain.room.domain.Room;
import com.example.prography_quest.domain.room.domain.Room_type;
import com.example.prography_quest.domain.room.dto.CreateRoomRequest;
import com.example.prography_quest.domain.room.repository.RoomRepository;
import com.example.prography_quest.domain.user.domain.User;
import com.example.prography_quest.domain.user.service.UserService;
import com.example.prography_quest.domain.userRoom.domain.UserRoom;
import com.example.prography_quest.domain.userRoom.repository.UserRoomRepository;
import com.example.prography_quest.domain.userRoom.service.UserRoomService;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateRoomApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private UserRoomRepository userRoomRepository;

    @Autowired
    private RoomRepository roomRepository;

    @DisplayName("CreateRoom api 테스트 성공")
    @Test
    public void createRooms() throws Exception {
        String url = "/init";
        ApiResponse<Void> response = new ApiResponse<Void>().ok();
        InitRequest request = InitRequest.builder()
                .seed(40)
                .quantity(10)
                .build();

        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        url = "/room";
        CreateRoomRequest createRoomRequest = CreateRoomRequest.builder()
                .roomType(Room_type.SINGLE)
                .userId(1)
                .title("a")
                .build();

        resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createRoomRequest)));

        List<UserRoom> list = userRoomRepository.findAll();
        assertThat(list.size()).isEqualTo(1);
        List<Room> dlist = roomRepository.findAll();
        assertThat(dlist.size()).isEqualTo(1);
    }
}
