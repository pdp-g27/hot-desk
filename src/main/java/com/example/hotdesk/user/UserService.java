package com.example.hotdesk.user;

import com.example.hotdesk.common.exception.PhoneNumberNotVerifiedException;
import com.example.hotdesk.common.service.GenericCrudService;
import com.example.hotdesk.jwt.JwtService;
import com.example.hotdesk.sms.SmsCodeService;
import com.example.hotdesk.sms.dto.SmsCodeSendDto;
import com.example.hotdesk.user.dto.*;
import com.example.hotdesk.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class UserService extends GenericCrudService<User, Integer, UserCreateDto, UserUpdateDto, UserPatchDto, UserResponseDto>
    implements UserDetailsService
{

    private final UserRepository repository;
    private final UserDtoMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final Class<User> entityClass = User.class;
    private final JwtService jwtService;
    private final SmsCodeService smsCodeService;

    @Override
    public UserDetails loadUserByUsername( String phone ) throws UsernameNotFoundException
    {
        return repository.findUserByPhoneNumber( phone )
                         .orElseThrow( () -> new BadCredentialsException( "bad credentials" ) );
    }

    @Override
    protected User save( UserCreateDto userCreateDto )
    {
        checkEmailAndPhone( userCreateDto.getEmail(), userCreateDto.getPhoneNumber() );
        User entity = mapper.toEntity( userCreateDto );
        entity.setPassword( passwordEncoder.encode( entity.getPassword() ) );
        User saved = repository.save( entity );
        smsCodeService.sendSms( new SmsCodeSendDto( saved.getPhoneNumber() ) );
        return saved;
    }

    @Override
    protected User updateEntity( UserUpdateDto userUpdateDto, User user )
    {
        mapper.update( userUpdateDto, user );
        return repository.save( user );
    }

    private void checkEmailAndPhone( String email, String phone )
    {
        Boolean existsByEmail = repository.existsByEmail( email );
        Boolean existsByPhoneNumber = repository.existsByPhoneNumber( phone );
        if( existsByEmail )
        {
            throw new DataIntegrityViolationException( "user with Email = %s already exists".formatted( email ) );
        }
        if( existsByPhoneNumber )
        {
            throw new DataIntegrityViolationException( "user with phone = %s already exists".formatted( phone ) );
        }
    }

    public UserSignInResponseDto signIn( UserSignInDto signInDto )
    {
        String phoneNumber = signInDto.getPhoneNumber();

        User user = repository.findUserByPhoneNumber( phoneNumber )
                              .orElseThrow( () -> new BadCredentialsException( "Username or password doesn't match" ) );

        if( passwordEncoder.matches( signInDto.getPassword(), user.getPassword() ) )
        {
            if( user.isPhoneNumberVerified() )
            {
                String token = jwtService.generateToken( user.getPhoneNumber() );
                UserResponseDto responseDto = mapper.toResponseDto( user );
                return new UserSignInResponseDto( responseDto, token );
            }
            throw new PhoneNumberNotVerifiedException( "%s is not verified. Please verify phone your phone number".formatted( user.getPhoneNumber() ) );
        }
        throw new BadCredentialsException( "Username or password doesn't match" );
    }
}
