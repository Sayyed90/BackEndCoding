package com.example.etiqatest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("roles")
    private List<RolesDTO> roles;
    @JsonProperty("email_id")
    private String email_id;
    @JsonProperty("address")
    private String address;
    @JsonIgnore
    private int id;

    public UserDTO(String myName, String s) {
        this.username=myName;
        this.password=s;
    }
}
