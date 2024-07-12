package com.travelbnbtest.travelbnbtest.repository;

import com.travelbnbtest.travelbnbtest.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}