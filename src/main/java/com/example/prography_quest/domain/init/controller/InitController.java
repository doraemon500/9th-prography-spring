package com.example.prography_quest.domain.init.controller;

import java.util.Collections;
import java.util.List;

import com.example.prography_quest.domain.init.dto.FakerApiResponse;
import com.example.prography_quest.domain.init.dto.InitRequest;
import com.example.prography_quest.domain.room.service.RoomService;
import com.example.prography_quest.domain.user.domain.Status;
import com.example.prography_quest.domain.user.domain.User;
import com.example.prography_quest.domain.user.service.UserService;
import com.example.prography_quest.domain.userRoom.service.UserRoomService;
import com.example.prography_quest.global.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RestController
@Tag(name = "Init", description = "초기화 API")
@RequiredArgsConstructor
public class InitController {
    private final UserService userService;
    private final RoomService roomService;
    private final UserRoomService userRoomService;

    @CrossOrigin
    @Tag(name = "Init", description = "초기화 작업 API")
    @PostMapping("/init")
    public ResponseEntity<ApiResponse> postInit(@RequestBody InitRequest initRequest) {
        userService.deleteAllUser();
        roomService.deleteAllRoom();
        userRoomService.deleteAllUserRoom();

        Mono<FakerApiResponse> responseMono = WebClient.create()
                .get()
                .uri(UriComponentsBuilder.fromUriString("https://fakerapi.it/api/v1/users")
                        .queryParam("_seed", initRequest.getSeed())
                        .queryParam("_quantity", initRequest.getQuantity())
                        .queryParam("_locale", "ko_KR")
                        .build()
                        .toUri())
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(FakerApiResponse.class);
                    } else {
                        throw new RuntimeException();
                    }
                });

        FakerApiResponse fakerApiResponse = responseMono.block();
        List<FakerApiResponse.Data> dlist = fakerApiResponse.getData();
        Collections.sort(dlist, (a, b) -> {
            return a.getId() - b.getId();
        });

        for (FakerApiResponse.Data data : dlist) {
            Status status = null;
            if (data.getId() <= 30)
                status = Status.ACTIVE;
            else if (data.getId() >= 31 && data.getId() <= 60)
                status = Status.WAIT;
            else if (data.getId() >= 61)
                status = Status.NON_ACTIVE;

            userService.saveUser(User.builder()
                    .fakerId(data.getId())
                    .name(data.getUsername())
                    .email(data.getEmail())
                    .status(status)
                    .build());
        }

        return ResponseEntity.ok().body(new ApiResponse<Void>().ok());
    }
}
