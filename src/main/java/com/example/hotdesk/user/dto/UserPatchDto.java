package com.example.hotdesk.user.dto;

import com.example.hotdesk.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPatchDto{

    private String firstName;

    private String lastName;

    private Role role;

    private String email;

    private String phoneNumber;
}
