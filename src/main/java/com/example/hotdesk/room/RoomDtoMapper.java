package com.example.hotdesk.room;

import com.example.hotdesk.common.service.GenericDtoMapper;
import com.example.hotdesk.room.dto.RoomCreateDto;
import com.example.hotdesk.room.dto.RoomResponseDto;
import com.example.hotdesk.room.dto.RoomUpdateDto;
import com.example.hotdesk.room.entity.Room;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomDtoMapper extends GenericDtoMapper<Room, RoomCreateDto, RoomUpdateDto, RoomResponseDto>
{
    private final ModelMapper mapper;

    @Override
    public Room toEntity( RoomCreateDto roomCreateDto )
    {
        return mapper.map( roomCreateDto, Room.class );
    }

    @Override
    public RoomResponseDto toResponseDto( Room room )
    {
        RoomResponseDto responseDto = mapper.map( room, RoomResponseDto.class );
        responseDto.setOfficeId( room.getOffice().getId() );
        return responseDto;
    }

    @Override
    public void update( RoomUpdateDto roomUpdateDto, Room room )
    {
        mapper.map( roomUpdateDto, room );
    }
}
