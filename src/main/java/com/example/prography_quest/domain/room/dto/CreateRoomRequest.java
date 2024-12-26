package com.example.prography_quest.domain.room.dto;

import com.example.prography_quest.domain.room.domain.Room_type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CreateRoomRequest {
    private Integer userId;
    private Room_type roomType;
    private String title;
}
