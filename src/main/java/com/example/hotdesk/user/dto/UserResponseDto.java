package com.example.hotdesk.user.dto;

import com.example.hotdesk.desk.entity.Desk;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto extends UserBaseDto{

    private Integer id;

    private List<Desk>desks;

    private String extraMessage;
}
