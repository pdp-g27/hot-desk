package com.example.hotdesk.office;

import com.example.hotdesk.common.configuration.CustomPageImpl;
import com.example.hotdesk.office.dto.AddressDto;
import com.example.hotdesk.office.dto.OfficeCreateDTo;
import com.example.hotdesk.office.dto.OfficeResponseDto;
import com.example.hotdesk.user.UserController;
import com.example.hotdesk.user.UserRepository;
import com.example.hotdesk.user.dto.UserResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OfficeControllerTest {

    @Autowired
    OfficeController officeController;

    @Autowired
    OfficeRepository officeRepository;


    @Autowired
    TestRestTemplate testRestTemplate;
    @Test
    void createOffice() {

        AddressDto addressDto = new AddressDto(
                "UZB",
                "Tashkent",
                "Beruniy",
                "3A");
        OfficeCreateDTo createDTo = new OfficeCreateDTo("Pdp Academy",addressDto);


        ResponseEntity<OfficeResponseDto> response = testRestTemplate.postForEntity("/office", createDTo, OfficeResponseDto.class);
        OfficeResponseDto body = response.getBody();

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(body);
        assertEquals(createDTo.getName(),body.getName());
    }

    @Test
    void getOffices() {
        ResponseEntity<CustomPageImpl<OfficeResponseDto>> response = testRestTemplate.exchange("/office?name==Pdp Academy", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<CustomPageImpl<OfficeResponseDto>>() {
        });

        System.out.println("response = " + response);

        assertEquals(response.getStatusCode(),HttpStatus.OK);
    }

    @Test
    void get() {
        ResponseEntity<OfficeResponseDto> forEntity = testRestTemplate.getForEntity("/user/{id}", OfficeResponseDto.class,1);

        Assertions.assertEquals(forEntity.getStatusCode().value(),200);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {

    }
}