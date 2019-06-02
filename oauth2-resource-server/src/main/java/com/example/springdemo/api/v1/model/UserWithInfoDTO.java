package com.example.springdemo.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithInfoDTO {
    private String username;
    private String password;

    @JsonProperty("user_url")
    private String userUrl;

    private String name;

    private String surname;

    private String telephone;
}
