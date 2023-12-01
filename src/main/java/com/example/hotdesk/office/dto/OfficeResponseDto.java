package com.example.hotdesk.office.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OfficeResponseDto
{
    private Integer id;
    private String name;
    private AddressResponseDto address;
}
