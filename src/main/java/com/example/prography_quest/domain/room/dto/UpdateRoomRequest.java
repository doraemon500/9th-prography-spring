package com.example.prography_quest.domain.room.dto;

import com.example.prography_quest.domain.room.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UpdateRoomRequest {
    private Status status;
}
