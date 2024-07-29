package com.travelbnbtest.travelbnbtest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;

    @Column(name = "status")
    private boolean status; // false means available, true means booked

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
    @Version
    private Integer version;

}