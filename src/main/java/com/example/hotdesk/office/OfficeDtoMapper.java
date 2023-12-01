package com.example.hotdesk.office;

import com.example.hotdesk.common.service.GenericDtoMapper;
import com.example.hotdesk.office.dto.OfficeCreateDTo;
import com.example.hotdesk.office.dto.OfficeResponseDto;
import com.example.hotdesk.office.dto.OfficeUpdateDto;
import com.example.hotdesk.office.entity.Office;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OfficeDtoMapper extends GenericDtoMapper<Office, OfficeCreateDTo, OfficeUpdateDto, OfficeResponseDto>
{
    private final ModelMapper mapper;

    @Override
    public Office toEntity( OfficeCreateDTo officeCreateDTo )
    {
        return mapper.map( officeCreateDTo, Office.class );
    }

    @Override
    public OfficeResponseDto toResponseDto( Office office )
    {
        return mapper.map( office, OfficeResponseDto.class );
    }

    @Override
    public void update( OfficeUpdateDto officeUpdateDto, Office office )
    {
        mapper.map( officeUpdateDto, office );
    }
}
