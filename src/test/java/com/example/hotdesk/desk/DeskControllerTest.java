package com.example.hotdesk.desk;

import com.example.hotdesk.common.configuration.CustomPageImpl;
import com.example.hotdesk.desk.dto.DeskCreateDto;
import com.example.hotdesk.desk.dto.DeskResponseDto;
import com.example.hotdesk.desk.dto.DeskUpdateDto;
import com.example.hotdesk.desk.entity.Accessories;
import com.example.hotdesk.office.dto.AddressDto;
import com.example.hotdesk.office.dto.OfficeCreateDTo;
import com.example.hotdesk.office.dto.OfficeResponseDto;
import com.example.hotdesk.room.dto.RoomCreateDto;
import com.example.hotdesk.room.dto.RoomResponseDto;
import com.example.hotdesk.room.entity.RoomType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeskControllerTest {
        @Container
        @ServiceConnection
        static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.0")
                .withUsername("postgres");

        @Autowired
        TestRestTemplate restTemplate;

        @Test
        @Order(1)
        void createDesk() {

            AddressDto addressDto=new AddressDto("Uzbekistan","Tashkent","Chorsu","6");
            OfficeCreateDTo officeCreateDTo=new OfficeCreateDTo("PDP",addressDto);

            ResponseEntity<OfficeResponseDto> officeResponseDtoResponseEntity = restTemplate.postForEntity("/office", officeCreateDTo, OfficeResponseDto.class);

            Assertions.assertEquals(officeResponseDtoResponseEntity.getStatusCode().value(),201);

            RoomCreateDto roomCreateDto=new RoomCreateDto(1,"2", RoomType.KITCHEN,2);

            ResponseEntity<RoomResponseDto> roomResponseDtoResponseEntity = restTemplate.postForEntity("/room", roomCreateDto, RoomResponseDto.class);

            Assertions.assertEquals(roomResponseDtoResponseEntity.getStatusCode().value(),201);

            DeskCreateDto createDto=new DeskCreateDto(1, List.of());
            ResponseEntity<DeskResponseDto> deskResponseDtoResponseEntity = restTemplate.postForEntity("/desk", createDto, DeskResponseDto.class);
            System.out.println("deskResponseDtoResponseEntity = " + deskResponseDtoResponseEntity);
            Assertions.assertEquals(deskResponseDtoResponseEntity.getStatusCode().value(),201);
        }

        @Test
        @Order(2)
        void get() {
            ResponseEntity<CustomPageImpl<DeskResponseDto>> responseEntity = restTemplate.exchange(
                    "/desk?roomId==1",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<CustomPageImpl<DeskResponseDto>>() {}
            );
            Assertions.assertEquals(responseEntity.getStatusCode().value(),200);
        }

        @Test
        @Order(3)
        void testGet() {
            ResponseEntity<DeskResponseDto> forEntity = restTemplate.getForEntity("/desk/{id}", DeskResponseDto.class,1);

            Assertions.assertEquals(forEntity.getStatusCode().value(),200);
        }

        @Test
        @Order(4)
        void testGet1() {
        }

        @Test
        @Order(5)
        void testPut() {
            DeskUpdateDto deskUpdateDto=new DeskUpdateDto(1,List.of(Accessories.ETHERNET));

            ResponseEntity<DeskResponseDto> responseEntity = restTemplate.exchange("/desk/%s".formatted(1), HttpMethod.PUT, new HttpEntity<>(deskUpdateDto), DeskResponseDto.class);
            Assertions.assertEquals(responseEntity.getStatusCode().value(),200);
        }

        @Test
        @Order(6)
        void delete() {
            restTemplate.delete("/desk/%s".formatted(1));
        }


}