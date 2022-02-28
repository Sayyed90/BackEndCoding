package com.example.etiqatest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RolesDTO {

    @JsonProperty("id")
    private String id;
    @JsonProperty("role")
    private String role;

}
