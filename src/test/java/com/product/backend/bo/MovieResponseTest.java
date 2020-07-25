package com.product.backend.bo;

import com.product.backend.BaseTest;
import com.product.backend.dto.MovieDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieResponseTest extends BaseTest {

    @Test
    public void unitTest_convertToBo_null_null() {
        MovieDto movieDto = null;
        MovieResponse movieResponse = MovieResponse.convertToBo(movieDto);
        assertNull(movieResponse);
    }

    @Test
    public void unitTest_convertToBo_valid_success() {
        MovieDto movieDto = createMovieDto();
        MovieResponse movieResponse = MovieResponse.convertToBo(movieDto);
        assertEquals(movieDto.getId(), movieResponse.getId());
        assertEquals(movieDto.getDescription(), movieResponse.getDescription());
        assertEquals(movieDto.getGenre(), movieResponse.getGenre());
        assertEquals(movieDto.getRank(), movieResponse.getRank());
        assertEquals(movieDto.getRating(), movieResponse.getRating());
        assertEquals(movieDto.getRunTime(), movieResponse.getRunTime());
        assertEquals(movieDto.getTitle(), movieResponse.getTitle());
        assertEquals(movieDto.getYear(), movieResponse.getYear());
    }

    @Test
    public void unitTest_convertToBo_validList_success() {
        MovieDto movieDto = createMovieDto();
        MovieDto movieDto1 = createMovieDto();
        movieDto1.setId(2L);
        movieDto1.setTitle("Title2");
        List<MovieDto> movieDtos = new ArrayList<>();
        movieDtos.add(movieDto);
        movieDtos.add(movieDto1);
        List<MovieResponse> movieResponses = MovieResponse.convertToBo(movieDtos);

        assertNotNull(movieResponses);
        assertEquals(movieDtos.size(), movieResponses.size());
        int index =0;
        for (MovieDto movieDto2 : movieDtos) {
            MovieResponse movieResponse = movieResponses.get(index++);
            assertEquals(movieDto2.getId(), movieResponse.getId());
            assertEquals(movieDto2.getDescription(), movieResponse.getDescription());
            assertEquals(movieDto2.getGenre(), movieResponse.getGenre());
            assertEquals(movieDto2.getRank(), movieResponse.getRank());
            assertEquals(movieDto2.getRating(), movieResponse.getRating());
            assertEquals(movieDto2.getRunTime(), movieResponse.getRunTime());
            assertEquals(movieDto2.getTitle(), movieResponse.getTitle());
            assertEquals(movieDto2.getYear(), movieResponse.getYear());
        }
    }
}
