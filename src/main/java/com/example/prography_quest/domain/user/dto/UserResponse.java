package com.example.prography_quest.domain.user.dto;

import com.example.prography_quest.domain.user.domain.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.prography_quest.domain.user.domain.User;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserResponse {
    private Integer totalElements;
    private Integer totalPages;
    private List<User> userList;
}
