package com.example.prography_quest.domain.init.dto;

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
    private List<Data> data;

    @Getter
    public static class Data {
        @JsonProperty
        private Integer id;
        @JsonProperty
        private String uuid;
        @JsonProperty
        private String firstname;
        @JsonProperty
        private String lastname;
        @JsonProperty
        private String username;
        @JsonProperty
        private String password;
        @JsonProperty
        private String email;
        @JsonProperty
        private String ip;
        @JsonProperty
        private String macAddress;
        @JsonProperty
        private String website;
        @JsonProperty
        private String image;
    }

}
