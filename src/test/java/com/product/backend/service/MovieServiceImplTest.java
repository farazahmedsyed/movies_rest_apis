package com.product.backend.service;

import com.product.backend.BaseTest;
import com.product.backend.dto.MovieDto;
import com.product.backend.model.Movie;
import com.product.backend.model.User;
import com.product.backend.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class MovieServiceImplTest extends BaseTest {
    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserService userService;

    private Movie movie;

    private static final Integer YEAR = 2012;
    private static final String GENRE = "GENRE";

    @BeforeEach
    public void setup(){
        movie = createMovie();
        movie.setYear(YEAR);
        movie.setGenre(GENRE);
    }

    @Test
    public void unitTest_search_valid_success(){
        final Integer endYear = 2016;
        doAnswer(invocationOnMock -> {
            assertEquals(YEAR, invocationOnMock.getArgument(0));
            assertEquals(endYear, invocationOnMock.getArgument(1));
            assertEquals(GENRE, invocationOnMock.getArgument(2));
            return Arrays.asList(movie);
        }).when(movieRepository).search(any(), any(), anyString());

        List<MovieDto> movieDtos = movieService.search(YEAR, endYear, GENRE);

        assertNotNull(movieDtos);
        assertEquals(1, movieDtos.size());
        MovieDto movieDto = movieDtos.get(0);
        assertEquals(movie.getId(), movieDto.getId());
        assertEquals(movie.getDescription(), movieDto.getDescription());
        assertEquals(GENRE, movieDto.getGenre());
        assertEquals(movie.getRank(), movieDto.getRank());
        assertEquals(movie.getRating(), movieDto.getRating());
        assertEquals(movie.getRunTime(), movieDto.getRunTime());
        assertEquals(movie.getTitle(), movieDto.getTitle());
        assertEquals(YEAR, movieDto.getYear());

        verify(movieRepository, times(1)).search(any(), any(), any());
    }

    @Test
    public void unitTest_saveAndConsumeDuplicate_allUnique_success() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File(CSV_FILE));
        Movie movie1 = createMovie1();
        Movie movie2 = createMovie2();
        List<String> titles = new ArrayList<>();
        titles.add("ZZZZ");
        when(movieRepository.findDistinctTiles()).thenReturn(titles);
        doAnswer(invocationOnMock -> {
            Set<User> users = invocationOnMock.getArgument(0);
            assertNotNull(users);
            assertEquals(4, users.size());
            List<User> users1 = new ArrayList<>(users);
            assertEquals(createDirector1(), users1.get(0));
            assertEquals(createActor1(), users1.get(1));
            assertEquals(createDirector2(), users1.get(2));
            assertEquals(createActor2(), users1.get(3));
            Map<String, User> map = new HashMap<>();
            map.put(users1.get(0).getName(), users1.get(0));
            map.put(users1.get(1).getName(), users1.get(1));
            map.put(users1.get(2).getName(), users1.get(2));
            map.put(users1.get(3).getName(), users1.get(3));
            return map;
        }).when(userService).getManagedEntities(any());
        doAnswer(invocationOnMock -> {
            assertNotNull(invocationOnMock.getArgument(0));
            List<Movie> movies = invocationOnMock.getArgument(0);
            assertEquals(movie1, movies.get(0));
            assertEquals(movie2, movies.get(1));
            return movies;
        }).when(movieRepository).saveAll(any());
        assertEquals(2, movieService.saveAndConsumeDuplicate(fileInputStream));
        verify(movieRepository, times(1)).findDistinctTiles();
        verify(movieRepository, times(1)).saveAll(any());
        verify(userService, times(1)).getManagedEntities(any());
    }

    @Test
    public void unitTest_saveAndConsumeDuplicate_oneUnique_success() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File(CSV_FILE));
        Movie movie1 = createMovie1();
        Movie movie2 = createMovie2();
        List<String> titles = new ArrayList<>();
        titles.add(movie1.getTitle());
        when(movieRepository.findDistinctTiles()).thenReturn(titles);

        doAnswer(invocationOnMock -> {
            Set<User> users = invocationOnMock.getArgument(0);
            assertNotNull(users);
            assertEquals(1, users.size());
            assertEquals(movie2.getDirector(), users.stream().findFirst().get());
            Map<String, User> map = new HashMap<>();
            map.put(movie2.getDirector().getName(), movie2.getDirector());
            return map;
        }).when(userService).getManagedEntities(any());
        doAnswer(invocationOnMock -> {
            assertNotNull(invocationOnMock.getArgument(0));
            List<Movie> movies = invocationOnMock.getArgument(0);
            assertEquals(1, movies.size());
            assertEquals(movie2, movies.get(0));
            return movies;
        }).when(movieRepository).saveAll(any());
        assertEquals(1, movieService.saveAndConsumeDuplicate(fileInputStream));
        verify(movieRepository, times(1)).findDistinctTiles();
        verify(movieRepository, times(1)).saveAll(any());
        verify(userService, times(1)).getManagedEntities(any());
    }

    @Test
    public void unitTest_saveAndConsumeDuplicate_NoUnique_success() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File(CSV_FILE));
        Movie movie1 = createMovie1();
        Movie movie2 = createMovie2();
        List<String> titles = new ArrayList<>();
        titles.add(movie1.getTitle());
        titles.add(movie2.getTitle());
        when(movieRepository.findDistinctTiles()).thenReturn(titles);
        assertEquals(0, movieService.saveAndConsumeDuplicate(fileInputStream));
        verify(movieRepository, times(1)).findDistinctTiles();
        verify(movieRepository, times(0)).saveAll(any());
        verify(userService, times(0)).getManagedEntities(any());
    }

}
