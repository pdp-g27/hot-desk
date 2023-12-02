package com.example.hotdesk.user;

import com.example.hotdesk.common.service.GenericCrudService;
import com.example.hotdesk.user.dto.UserCreateDto;
import com.example.hotdesk.user.dto.UserPatchDto;
import com.example.hotdesk.user.dto.UserResponseDto;
import com.example.hotdesk.user.dto.UserUpdateDto;
import com.example.hotdesk.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class UserService extends GenericCrudService<User,Integer, UserCreateDto, UserUpdateDto, UserPatchDto, UserResponseDto>
                            implements UserDetailsService {

    private final UserRepository repository;
    private final UserDtoMapper mapper;
    private final Class<User>entityClass= User.class;


    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return repository.findUserByPhoneNumber(phone)
                .orElseThrow(() -> new BadCredentialsException("bad credentials"));
    }

    @Override
    protected User save(UserCreateDto userCreateDto) {
        checkEmailAndPhone(userCreateDto.getEmail(), userCreateDto.getPhoneNumber());
        User entity = mapper.toEntity(userCreateDto);
        return repository.save(entity);
    }

    @Override
    protected User updateEntity(UserUpdateDto userUpdateDto, User user) {
        mapper.update(userUpdateDto,user);
        return repository.save(user);
    }

    private void checkEmailAndPhone(String email, String phone){
        Boolean existsByEmail = repository.existsByEmail(email);
        Boolean existsByPhoneNumber = repository.existsByPhoneNumber(phone);
        if (existsByEmail) {
            throw new DataIntegrityViolationException("user with Email = %s already exists".formatted(email));
        }
        if (existsByPhoneNumber) {
            throw new DataIntegrityViolationException("user with phone = %s already exists".formatted(phone));
        }
    }
}
