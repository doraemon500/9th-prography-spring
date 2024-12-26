package com.example.prography_quest.domain.room.dto;

import com.example.prography_quest.domain.room.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RoomResponse {
    private Integer totalElements;
    private Integer totalPages;
    private List<Room> roomList;
}

