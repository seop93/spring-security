package com.example.springlogin2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Getter
public class UserJoinRequest {
    private String userName;
    private String password;
}
