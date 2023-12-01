package com.example.hotdesk.desk.entity;

import com.example.hotdesk.room.entity.Room;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Desk
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    @Enumerated( EnumType.STRING )
    private List<Accessories> accessories;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Room room;
}
