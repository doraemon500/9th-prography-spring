package com.example.prography_quest;

import com.example.prography_quest.domain.init.dto.InitRequest;
import com.example.prography_quest.domain.user.domain.User;
import com.example.prography_quest.domain.user.dto.UserResponse;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FindUserApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService userService;

    @DisplayName("findUser api 테스트 성공")
    @Test
    public void findUsers() throws Exception {
        String url = "/init";
        ApiResponse<Void> response = new ApiResponse<Void>().ok(ExceptionCode.OK);
        InitRequest request = InitRequest.builder()
                .seed(40)
                .quantity(10)
                .build();

        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        url = "/user";
        int size = 10;
        int page = 0;

        resultActions = mockMvc.perform(get(url)
                .param("size", String.valueOf(size))
                .param("page", String.valueOf(page))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        List<User> list = userService.findAllUser();
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.totalElements").value(list.size()));
    }
}
