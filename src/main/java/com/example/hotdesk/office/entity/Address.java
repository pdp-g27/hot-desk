package com.example.hotdesk.office.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;
    private String country;
    private String city;
    private String street;
    private String buildingNumber;
    @OneToOne( mappedBy = "address" )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Office office;
}
