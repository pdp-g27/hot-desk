package com.example.hotdesk.room;

import com.example.hotdesk.common.service.GenericCrudService;
import com.example.hotdesk.office.OfficeRepository;
import com.example.hotdesk.office.entity.Office;
import com.example.hotdesk.room.dto.RoomCreateDto;
import com.example.hotdesk.room.dto.RoomPatchDto;
import com.example.hotdesk.room.dto.RoomResponseDto;
import com.example.hotdesk.room.dto.RoomUpdateDto;
import com.example.hotdesk.room.entity.Room;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Service
@RequiredArgsConstructor
@Transactional
public class RoomService extends GenericCrudService<Room, Integer, RoomCreateDto, RoomUpdateDto, RoomPatchDto, RoomResponseDto>
{
    private final RoomDtoMapper mapper;
    private final RoomRepository repository;
    private final OfficeRepository officeRepository;
    private final Class<Room> entityClass = Room.class;

    @Override
    protected Room save( RoomCreateDto dto )
    {
        Room entity = mapper.toEntity( dto );

        Office office = officeRepository
            .findById( dto.getOfficeId() )
            .orElseThrow(
                () -> throwEntityNotFoundException( dto.getOfficeId(), Office.class.getSimpleName() )
            );

        boolean roomNumberAlreadyExists = office.getRooms()
                                                .stream()
                                                .anyMatch( room -> room.getNumber().equals( entity.getNumber() ) );

        if( roomNumberAlreadyExists )
        {
            throw new DataIntegrityViolationException(
                "In office=%s room with number=%s is already exists"
                    .formatted( office.getId(), entity.getNumber())
            );
        }

        entity.setOffice( office );
        return repository.save( entity );
    }

    @Override
    protected Room updateEntity( RoomUpdateDto roomUpdateDto, Room room )
    {
        mapper.update( roomUpdateDto, room );

        Office office = officeRepository
            .findById( roomUpdateDto.getOfficeId() )
            .orElseThrow(
                () -> throwEntityNotFoundException( roomUpdateDto.getOfficeId(), Office.class.getSimpleName() )
            );

        boolean roomNumberAlreadyExists = office.getRooms()
                                                .stream()
                                                .anyMatch( officeRoom -> officeRoom.getNumber().equals( room.getNumber() ) && !officeRoom.getId().equals( room.getId() ) );

        if( roomNumberAlreadyExists )
        {
            throw new DataIntegrityViolationException(
                "In office=%s room with number=%s is already exists"
                    .formatted( office.getId(), room.getNumber() )
            );
        }

        room.setOffice( office );

        return repository.save( room );
    }
}
