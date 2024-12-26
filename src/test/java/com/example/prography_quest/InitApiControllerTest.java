package com.example.prography_quest;

import com.example.prography_quest.domain.init.dto.InitRequest;
import com.example.prography_quest.domain.user.domain.User;
import com.example.prography_quest.domain.user.service.UserService;
import com.example.prography_quest.global.common.exception.ExceptionCode;
import com.example.prography_quest.global.common.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InitApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService userService;

    @DisplayName("Health 체크 api 테스트 성공")
    @Test
    public void checkHealth() throws Exception {
        final String url = "/health";
        ApiResponse<Void> response = new ApiResponse<Void>().ok(ExceptionCode.OK);

        final ResultActions resultActions = mockMvc.perform(get(url));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(response.getCode()))
                .andExpect(jsonPath("$.message").value(response.getMessage()));
    }

    @DisplayName("Init api 테스트 성공")
    @Test
    public void checkInit() throws Exception {
        final String url = "/init";
        ApiResponse<Void> response = new ApiResponse<Void>().ok(ExceptionCode.OK);
        InitRequest request = InitRequest.builder()
                .seed(40)
                .quantity(50)
                .build();

        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(response.getCode()))
                .andExpect(jsonPath("$.message").value(response.getMessage()));

        List<User> list = userService.findAllUser();
        assertThat(list.size()).isEqualTo(request.getQuantity());
//        for(User user : list)
//           assertThat(user.getId()).isEqualTo(user.getFakerId());
    }
}
