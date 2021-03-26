package com.example.demo.entity;
import com.example.demo.enumeration.Country;
import lombok.*;
import javax.persistence.*;

@EqualsAndHashCode
@ToString
@Getter
@Entity
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    private String title;

    @Setter
    private Country country;

    @Setter
    private boolean light;

    public Room (String title, Country country) {
        this.title = title;
        this.country = country;
    }
}
