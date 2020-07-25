package com.product.backend.bo;

import com.product.backend.dto.MovieDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class MovieResponse {

    private Long id;
    private Integer rank;
    private String title;
    private String genre;
    private Integer year;
    private String runTime;
    private Double rating;
    private String description;

    public static MovieResponse convertToBo(MovieDto movieDto) {
        MovieResponse movieResponse = null;
        if (Boolean.FALSE.equals(Objects.isNull(movieDto))) {
            movieResponse = new MovieResponse();
            BeanUtils.copyProperties(movieDto, movieResponse);
        }
        return movieResponse;
    }

    public static List<MovieResponse> convertToBo(List<MovieDto> movieDtos) {
        List<MovieResponse> movieResponses = new ArrayList<>();
        for (MovieDto movieDto : movieDtos) {
            movieResponses.add(convertToBo(movieDto));
        }
        return movieResponses;
    }
}
