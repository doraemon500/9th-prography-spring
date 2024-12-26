package com.example.prography_quest.domain.init.dto;

import com.example.prography_quest.domain.init.domain.Faker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FakerApiResponse {
    private String status;
    private Integer code;
    private Integer total;
    private List<Faker> data;
}
