package com.example.hotdesk.office.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeCreateDTo
{
    private String name;
    private AddressDto address;
}
