package com.example.hotdesk.desk;

import com.example.hotdesk.common.service.GenericDtoMapper;
import com.example.hotdesk.desk.dto.DeskCreateDto;
import com.example.hotdesk.desk.dto.DeskResponseDto;
import com.example.hotdesk.desk.dto.DeskUpdateDto;
import com.example.hotdesk.desk.entity.Desk;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeskDtoMapper extends GenericDtoMapper<Desk, DeskCreateDto, DeskUpdateDto, DeskResponseDto>
{
    private final ModelMapper mapper;

    @Override
    public Desk toEntity( DeskCreateDto deskCreateDto )
    {
        return mapper.map( deskCreateDto, Desk.class );
    }

    @Override
    public DeskResponseDto toResponseDto( Desk desk )
    {
        DeskResponseDto responseDto = mapper.map( desk, DeskResponseDto.class );
        responseDto.setRoomId( desk.getRoom().getId() );
        return responseDto;
    }

    @Override
    public void update( DeskUpdateDto deskUpdateDto, Desk desk )
    {
        mapper.map( deskUpdateDto, desk );
    }
}
