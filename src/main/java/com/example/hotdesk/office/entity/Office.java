package com.example.hotdesk.office.entity;

import com.example.hotdesk.room.entity.Room;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Office
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;
    @OneToMany( mappedBy = "office" )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Room> rooms;
    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne( cascade = CascadeType.ALL )
    private Address address;
}
