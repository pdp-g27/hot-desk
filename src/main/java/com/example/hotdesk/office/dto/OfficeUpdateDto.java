package com.example.hotdesk.office.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OfficeUpdateDto
{
    private String name;
    private AddressDto address;
}
