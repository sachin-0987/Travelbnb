package com.travelbnbtest.travelbnbtest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "image_url", nullable = false, length = 2000)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

}