package com.example.hotdesk.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserCreateDto extends UserBaseDto {

    @NotBlank(message = "auth.user.password.required")
    private String password;

}
