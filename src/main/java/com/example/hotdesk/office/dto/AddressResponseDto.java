package com.example.hotdesk.office.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode( callSuper = true )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto extends AddressDto
{
    private Integer id;
}
