package com.product.backend.service;

import com.product.backend.dto.MovieDto;
import com.product.backend.helper.CSVHelper;
import com.product.backend.model.Movie;
import com.product.backend.model.User;
import com.product.backend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserService userService;

    @Override
    public List<MovieDto> search(Integer startYear, Integer endYear, String genre) {
        return Movie.convertToDto(movieRepository.search(startYear, endYear, genre));
    }

    @Override
    public Integer saveAndConsumeDuplicate(InputStream inputStream) {
        List<Movie> movies = CSVHelper.csvToMovies(inputStream);
        List<String> tiles = movieRepository.findDistinctTiles();
        movies = movies.stream().filter(movie -> !tiles.contains(movie.getTitle())).collect(Collectors.toList());
        if (movies.isEmpty()) {
            return 0;
        }
        Set<User> users = new HashSet<>();
        for (Movie movie : movies) {
            users.add(movie.getDirector());
            users.addAll(movie.getActors());
        }
        Map<String, User> userMap = userService.getManagedEntities(users);
        for (Movie movie : movies) {
            movie.setDirector(userMap.get(movie.getDirector().getName()));
            Set<User> actors = new HashSet<>();
            for (User actor : movie.getActors()) {
                actors.add(userMap.get(actor.getName()));
            }
            movie.setActors(actors);
        }
        return movieRepository.saveAll(movies).size();
    }

}
