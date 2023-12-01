package com.example.hotdesk.room.dto;

import com.example.hotdesk.room.entity.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomPatchDto
{
    @NotNull
    private Integer officeId;
    @NotBlank
    private String number;
    private RoomType roomType;
    @NotNull
    private Integer floorNumber;
}
