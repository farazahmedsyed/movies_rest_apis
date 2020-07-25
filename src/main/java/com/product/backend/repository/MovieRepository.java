package com.product.backend.repository;

import com.product.backend.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT movie from Movie movie join fetch movie.director left join fetch movie.actors" +
            " where movie.genre = :genre and movie.year >= :startYear and movie.year <= :endYear order by movie.rank")
    List<Movie> search(Integer startYear, Integer endYear, String genre);

    @Query("select distinct movie.title from Movie movie")
    List<String> findDistinctTiles();
}
