package com.example.prography_quest.domain.room.dto;

import com.example.prography_quest.domain.init.dto.FakerApiResponse;
import com.example.prography_quest.domain.room.domain.Room;
import com.example.prography_quest.domain.room.domain.Room_type;
import com.example.prography_quest.domain.room.domain.Status;
import jakarta.persistence.criteria.CriteriaBuilder;
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

