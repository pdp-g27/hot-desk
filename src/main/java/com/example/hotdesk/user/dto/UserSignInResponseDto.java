package com.example.hotdesk.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignInResponseDto
{
    private UserResponseDto userResponseDto;
    private String token;
}
