package com.example.prography_quest.domain.init.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class InitRequest {
    private int seed;
    private int quantity;
}
