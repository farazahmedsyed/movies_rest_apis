package com.product.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovieDto {
    private Long id;
    private Integer rank;
    private String title;
    private String genre;
    private Integer year;
    private String runTime;
    private Double rating;
    private String description;
    private LocalDateTime updatedTime;
    private LocalDateTime createdTime;
}
