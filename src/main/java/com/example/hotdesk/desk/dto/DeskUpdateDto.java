package com.example.hotdesk.desk.dto;

import com.example.hotdesk.desk.entity.Accessories;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeskUpdateDto
{
    @NotNull
    private Integer roomId;
    private List<Accessories> accessories;
}
