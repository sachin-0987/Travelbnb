package com.travelbnbtest.travelbnbtest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

}