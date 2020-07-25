package com.product.backend.model;

import com.product.backend.BaseTest;
import com.product.backend.dto.MovieDto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTest extends BaseTest {
    
    @Test
    public void unitTest_convertToDto_null_null(){
        Movie movie = null;
        assertNull(Movie.convertToDto(movie));
    }

    @Test
    public void unitTest_convertToDto_valid_success(){
        Movie movie = createMovie();
        MovieDto movieDto = Movie.convertToDto(movie);
        assertEquals(movie.getId(), movieDto.getId());
        assertEquals(movie.getDescription(), movieDto.getDescription());
        assertEquals(movie.getGenre(), movieDto.getGenre());
        assertEquals(movie.getRank(), movieDto.getRank());
        assertEquals(movie.getRating(), movieDto.getRating());
        assertEquals(movie.getRunTime(), movieDto.getRunTime());
        assertEquals(movie.getTitle(), movieDto.getTitle());
        assertEquals(movie.getYear(), movieDto.getYear());
        assertEquals(movie.getUpdatedTime(), movieDto.getUpdatedTime());
        assertEquals(movie.getCreatedTime(), movieDto.getCreatedTime());
    }

    @Test
    public void unitTest_convertToDto_validList_success(){
        Movie movie1 = createMovie();
        Movie movie2 = createMovie();
        movie2.setId(2L);
        movie2.setTitle("Title2");
        List<Movie> movies = Arrays.asList(movie1, movie2);
        List<MovieDto> movieDtos = Movie.convertToDto(movies);
        assertNotNull(movieDtos);
        assertEquals(movies.size(), movieDtos.size());
        int index=0;
        for (Movie movie : movies) {
            MovieDto movieDto = movieDtos.get(index++);
            assertEquals(movie.getId(), movieDto.getId());
            assertEquals(movie.getDescription(), movieDto.getDescription());
            assertEquals(movie.getGenre(), movieDto.getGenre());
            assertEquals(movie.getRank(), movieDto.getRank());
            assertEquals(movie.getRating(), movieDto.getRating());
            assertEquals(movie.getRunTime(), movieDto.getRunTime());
            assertEquals(movie.getTitle(), movieDto.getTitle());
            assertEquals(movie.getYear(), movieDto.getYear());
        }
    }
}
