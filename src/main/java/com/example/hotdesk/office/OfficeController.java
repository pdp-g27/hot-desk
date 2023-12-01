package com.example.hotdesk.office;

import com.example.hotdesk.office.dto.OfficeCreateDTo;
import com.example.hotdesk.office.dto.OfficeResponseDto;
import com.example.hotdesk.office.dto.OfficeUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/office")
public class OfficeController
{
    private final OfficeService officeService;

    @PostMapping
    public ResponseEntity<OfficeResponseDto> createOffice( @RequestBody @Valid OfficeCreateDTo createDTo )
    {
        OfficeResponseDto officeResponseDto = officeService.create( createDTo );
        return ResponseEntity.status( HttpStatus.CREATED ).body( officeResponseDto );
    }

    @GetMapping
    public ResponseEntity<Page<OfficeResponseDto>> getOffices( Pageable pageable, @RequestParam( required = false ) String predicate )
    {
        Page<OfficeResponseDto> all = officeService.getAll( pageable, predicate );
        return ResponseEntity.ok( all );
    }

    @GetMapping( "/{id}" )
    public ResponseEntity<OfficeResponseDto> get( @PathVariable( "id" ) Integer id )
    {
        OfficeResponseDto responseDto = officeService.getById( id );
        return ResponseEntity.ok( responseDto );
    }

    @PutMapping( "/{id}" )
    public ResponseEntity<OfficeResponseDto> update( @PathVariable( "id" ) Integer id, @RequestBody @Valid OfficeUpdateDto updateDto )
    {
        OfficeResponseDto responseDto = officeService.update( id, updateDto );
        return ResponseEntity.ok( responseDto );
    }

    @DeleteMapping( "/{id}" )
    public ResponseEntity<?> delete( @PathVariable( "id" ) Integer id )
    {
        officeService.delete( id );
        return ResponseEntity.status( HttpStatus.NO_CONTENT ).build();
    }

}
