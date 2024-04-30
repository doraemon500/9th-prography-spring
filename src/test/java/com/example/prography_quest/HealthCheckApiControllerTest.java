package com.example.prography_quest;

import com.example.prography_quest.global.common.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HealthCheckApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @DisplayName("Health 체크 api 테스트 성공")
    @Test
    public void checkHealth() throws Exception {
        final String url = "/health";
        ApiResponse<Void> response = new ApiResponse<Void>().ok();

        final ResultActions resultActions = mockMvc.perform(get(url));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(response.getCode()))
                .andExpect(jsonPath("$.message").value(response.getMessage()));
    }
}
