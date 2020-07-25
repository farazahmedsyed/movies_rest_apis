package com.product.backend;

import com.product.backend.dto.MovieDto;
import com.product.backend.model.Movie;
import com.product.backend.model.User;

import java.time.LocalDateTime;
import java.util.HashSet;

public abstract class BaseTest {
    public static final String CSV_FILE = "src/test/resources/movies.csv";

    protected MovieDto createMovieDto() {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(1L);
        movieDto.setDescription("desc");
        movieDto.setTitle("tit");
        movieDto.setRank(1);
        movieDto.setGenre("genre");
        movieDto.setRating(4.5);
        movieDto.setRunTime("1 hr");
        movieDto.setYear(2012);
        return movieDto;
    }

    protected User createDirector1(){
        User user = new User();
        user.setName("David");
        return user;
    }
    protected User createDirector2(){
        User user = new User();
        user.setName("Director");
        return user;
    }
    protected User createActor1(){
        User user = new User();
        user.setName("Actor1");
        return user;
    }
    protected User createActor2(){
        User user = new User();
        user.setName("Wil");
        return user;
    }
    protected Movie createMovie1() {
        Movie expectedMovie1 = createMovie();
        expectedMovie1.setRank(1);
        expectedMovie1.setTitle("Dragon");
        expectedMovie1.setGenre("Fight Cartoon");
        expectedMovie1.setDescription("Cartoon");
        expectedMovie1.setYear(2012);
        expectedMovie1.setRunTime("2 hr");
        expectedMovie1.setRating(4.5);
        expectedMovie1.setDirector(createDirector1());
        expectedMovie1.setActors(new HashSet<>());
        expectedMovie1.getActors().add(createActor1());
        expectedMovie1.getActors().add(createActor2());
        return expectedMovie1;
    }

    protected Movie createMovie2() {
        Movie expectedMovie2 = createMovie();
        expectedMovie2.setRank(2);
        expectedMovie2.setTitle("Pokemon");
        expectedMovie2.setGenre("Cartoon");
        expectedMovie2.setDescription("Nice");
        expectedMovie2.setYear(2014);
        expectedMovie2.setRunTime("1hr");
        expectedMovie2.setRating(4.0);
        expectedMovie2.setDirector(createDirector2());
        expectedMovie2.setActors(new HashSet<>());
        return expectedMovie2;
    }

    protected Movie createMovie() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setDescription("desc");
        movie.setTitle("tit");
        movie.setRank(1);
        movie.setGenre("genre");
        movie.setRating(4.5);
        movie.setRunTime("1 hr");
        movie.setYear(2012);
        movie.setUpdatedTime(LocalDateTime.now());
        movie.setCreatedTime(LocalDateTime.now().minusDays(2));
        return movie;
    }
}
