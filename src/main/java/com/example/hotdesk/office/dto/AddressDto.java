package com.example.hotdesk.office.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto
{
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    private String buildingNumber;
}
