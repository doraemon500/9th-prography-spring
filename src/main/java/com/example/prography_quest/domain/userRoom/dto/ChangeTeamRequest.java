package com.example.prography_quest.domain.userRoom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ChangeTeamRequest {
    private Integer userId;
}
