package com.example.hotdesk.user;

import com.example.hotdesk.common.configuration.CustomPageImpl;
import com.example.hotdesk.enums.Role;
import com.example.hotdesk.user.dto.UserCreateDto;
import com.example.hotdesk.user.dto.UserPatchDto;
import com.example.hotdesk.user.dto.UserResponseDto;
import com.example.hotdesk.user.dto.UserUpdateDto;
import com.example.hotdesk.user.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    UserController userController;

    @Autowired
    UserRepository userRepository;


    @Autowired
    TestRestTemplate testRestTemplate;


    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.0")
            .withUsername("postgres")
            .withPassword("123");
    @Test
    @Order(1)
    void createUser() {

        UserCreateDto userCreateDto = new UserCreateDto();
        System.out.println(postgreSQLContainer.getJdbcUrl());
        System.out.println(postgreSQLContainer.getUsername());
        System.out.println(postgreSQLContainer.getPassword());

        userCreateDto.setFirstName("Anvar");
        userCreateDto.setLastName("Aliyev");
        userCreateDto.setPassword("123");
        userCreateDto.setPhoneNumber("998912178220");
        userCreateDto.setEmail("anvar@mail.ru");
        userCreateDto.setRole(Role.USER);


        ResponseEntity<UserResponseDto> response = testRestTemplate.postForEntity("/user", userCreateDto, UserResponseDto.class);


     /*
        HttpHeaders headers = new HttpHeaders();
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiI5OTg5MTIxNzgyMjAiLCJpYXQiOjE3MDE2NjY5NzIsImV4cCI6MTcwMTkyNjE3MiwiaXNzIjoid3d3LmhvdC1kZXNrLmNvbSJ9.1rnpN5VfdRVM3ID4X50rtHs9DkgnWfuwGJaZOCCf1AV_db1yCSR59roPlUYlZI5H";
        headers.setBearerAuth(token);
        HttpEntity<UserCreateDto> requestEntity = new HttpEntity<>(userCreateDto, headers);

      ResponseEntity<UserResponseDto> response = testRestTemplate.exchange(
                "/user",
                HttpMethod.POST,
                requestEntity,
                UserResponseDto.class
        );
    */
        UserResponseDto body = response.getBody();

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(body);
        assertEquals(userCreateDto.getFirstName(),body.getFirstName());
    }

    @Test
    @Disabled
    @Order(2)
    void getAllUser() {
        ResponseEntity<CustomPageImpl<UserResponseDto>> response = testRestTemplate.exchange("/user?role==ADMIN", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<CustomPageImpl<UserResponseDto>>() {
        });

        System.out.println("response = " + response);

        assertEquals(response.getStatusCode(),HttpStatus.OK);

    }

    @Test
    @Order(3)
    void getUser() {
        ResponseEntity<UserResponseDto> forEntity = testRestTemplate.getForEntity("/user/{id}", UserResponseDto.class,1);

        Assertions.assertEquals(forEntity.getStatusCode().value(),200);
    }

    @Test
    void updateUser() {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setFirstName("Ali");
        userUpdateDto.setLastName("Valiyev");
        userUpdateDto.setEmail("alivali@mail.ru");
        userUpdateDto.setPhoneNumber("998977070770");
        userUpdateDto.setRole(Role.ADMIN);

        ResponseEntity<UserResponseDto> responseEntity = testRestTemplate.exchange("/user/%s".formatted(1), HttpMethod.PUT, new HttpEntity<>(userUpdateDto), UserResponseDto.class);

        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
    }


    @Test
    void patchUser() {
        UserPatchDto userPatchDto = new UserPatchDto();
        userPatchDto.setEmail("checkPatch@mail.ru");
        ResponseEntity<UserResponseDto> responseEntity = testRestTemplate.exchange("/user/%s".formatted(1), HttpMethod.PATCH, new HttpEntity<>(userPatchDto), UserResponseDto.class);

        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        assertEquals(responseEntity.getBody().getEmail(),userPatchDto.getEmail());
    }

    @Test
    @Order(4)
    @Disabled
    void delete() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        User user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("userRepository is empty"));

        testRestTemplate.exchange("/user/%s".formatted(user.getId()),HttpMethod.DELETE,entity, String.class);

        Optional<User> optionalUser = userRepository.findById(user.getId());

        assertTrue(optionalUser.isEmpty());
    }
}