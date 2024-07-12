package com.travelbnbtest.travelbnbtest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "no_guests", nullable = false)
    private Integer noGuests;

    @Column(name = "no_bedrooms", nullable = false)
    private Integer noBedrooms;

    @Column(name = "no_bathrooms", nullable = false)
    private Integer noBathrooms;

    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

}