package com.example.hotdesk.room;

import com.example.hotdesk.room.dto.RoomCreateDto;
import com.example.hotdesk.room.dto.RoomPatchDto;
import com.example.hotdesk.room.dto.RoomResponseDto;
import com.example.hotdesk.room.dto.RoomUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/room" )
@RequiredArgsConstructor
public class RoomController
{
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponseDto> createRoom( @RequestBody @Valid RoomCreateDto roomCreateDto )
    {
        RoomResponseDto roomResponseDto = roomService.create( roomCreateDto );
        return ResponseEntity.status( HttpStatus.CREATED ).body( roomResponseDto );
    }

    @GetMapping
    public ResponseEntity<Page<RoomResponseDto>> getAllRoom( Pageable pageable, @RequestParam( required = false ) String predicate )
    {
        Page<RoomResponseDto> all = roomService.getAll( pageable, predicate );
        return ResponseEntity.ok( all );
    }

    @GetMapping( "/{id}" )
    public ResponseEntity<RoomResponseDto> getRoom( @PathVariable Integer id )
    {
        RoomResponseDto responseDto = roomService.getById( id );
        return ResponseEntity.ok( responseDto );
    }

    @PutMapping( "/{id}" )
    public ResponseEntity<RoomResponseDto> updateRoom( @PathVariable Integer id, @RequestBody @Valid RoomUpdateDto updateDto )
    {
        RoomResponseDto responseDto = roomService.update( id, updateDto );
        return ResponseEntity.ok( responseDto );
    }

    @PatchMapping( "/{id}" )
    public ResponseEntity<RoomResponseDto> patchRoom( @PathVariable Integer id, @RequestBody RoomPatchDto patchDto ) throws NoSuchFieldException, IllegalAccessException
    {
        RoomResponseDto responseDto = roomService.patch( id, patchDto );
        return ResponseEntity.ok( responseDto );
    }

    @DeleteMapping( "/{id}" )
    public ResponseEntity<?> delete( @PathVariable Integer id )
    {
        roomService.delete( id );
        return ResponseEntity.status( HttpStatus.NO_CONTENT ).build();
    }
}
