package com.example.hotdesk.desk.dto;

import com.example.hotdesk.desk.entity.Accessories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeskResponseDto
{
    private int id;
    private Integer roomId;
    private List<Accessories> accessories;
}
