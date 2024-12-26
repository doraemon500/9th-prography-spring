package com.example.prography_quest.domain.init.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Faker {
    private Integer id;
    private String uuid;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String ip;
    private String macAddress;
    private String website;
    private String image;
}
