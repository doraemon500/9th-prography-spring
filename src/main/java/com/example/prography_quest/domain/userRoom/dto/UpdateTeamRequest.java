package com.example.prography_quest.domain.userRoom.dto;

import com.example.prography_quest.domain.userRoom.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UpdateTeamRequest {
    private Team team;
}
