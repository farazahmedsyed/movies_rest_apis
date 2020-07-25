package com.product.backend.model;

import com.product.backend.dto.MovieDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "movies")
@Data
public class Movie extends BaseEntity {

    @Column(name = "rank")
    private Integer rank;
    @Column(name = "title", unique = true)
    private String title;
    @Column(name = "genre")
    private String genre;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "year")
    private Integer year;
    @Column(name = "run_time")
    private String runTime;
    @Column(name = "rating")
    private Double rating;

    @ManyToOne
    @JoinColumn(name = "director_id", referencedColumnName = "id")
    private User director;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_actors",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> actors;

    public static List<MovieDto> convertToDto(List<Movie> movies) {
        List<MovieDto> movieDtos = new ArrayList<>();
        for (Movie movie : movies) {
            movieDtos.add(convertToDto(movie));
        }
        return movieDtos;
    }

    public static MovieDto convertToDto(Movie movie) {
        MovieDto movieDto = null;
        if (Boolean.FALSE.equals(Objects.isNull(movie))) {
            movieDto = new MovieDto();
            BeanUtils.copyProperties(movie, movieDto);
        }
        return movieDto;
    }
}
