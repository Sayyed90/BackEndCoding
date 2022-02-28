package com.example.etiqatest.payload;

import java.util.List;
import java.util.Set;

public class ResponsePayload {
    long id;
    List<String> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
