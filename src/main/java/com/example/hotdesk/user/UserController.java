package com.example.hotdesk.user;

import com.example.hotdesk.user.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/user" )
@RequiredArgsConstructor
public class UserController
{

    private final UserService userService;

    @PostMapping( "/sign-in" )
    public ResponseEntity<UserSignInResponseDto> signIn( @RequestBody @Valid UserSignInDto signInDto )
    {
        UserSignInResponseDto userResponseDto = userService.signIn( signInDto );
        return ResponseEntity
            .ok( userResponseDto );
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser( @RequestBody @Valid UserCreateDto userCreateDto )
    {
        UserResponseDto userResponseDto = userService.create( userCreateDto );
        return ResponseEntity
            .status( HttpStatus.CREATED )
            .body( userResponseDto );
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAllUser( Pageable pageable, @RequestParam( required = false ) String predicate )
    {
        Page<UserResponseDto> all = userService.getAll( pageable, predicate );
        return ResponseEntity.ok( all );
    }

    @GetMapping( "/{id}" )
    public ResponseEntity<UserResponseDto> getUser( @PathVariable Integer id )
    {
        UserResponseDto responseDto = userService.getById( id );
        return ResponseEntity.ok( responseDto );
    }

    @PutMapping( "/{id}" )
    public ResponseEntity<UserResponseDto> updateUser( @PathVariable Integer id, @RequestBody @Valid UserUpdateDto updateDto )
    {
        UserResponseDto responseDto = userService.update( id, updateDto );
        return ResponseEntity.ok( responseDto );
    }

    @PatchMapping( "/{id}" )
    public ResponseEntity<UserResponseDto> patchUser( @PathVariable Integer id, @RequestBody UserPatchDto patchDto ) throws NoSuchFieldException, IllegalAccessException
    {
        UserResponseDto responseDto = userService.patch( id, patchDto );
        return ResponseEntity.ok( responseDto );
    }

    @DeleteMapping( "/{id}" )
    public ResponseEntity<?> delete( @PathVariable Integer id )
    {
        userService.delete( id );
        return ResponseEntity.status( HttpStatus.NO_CONTENT ).build();
    }
}
