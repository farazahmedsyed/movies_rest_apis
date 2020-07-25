package com.product.backend.service;

import com.product.backend.dto.MovieDto;

import java.io.InputStream;
import java.util.List;

public interface MovieService {
    List<MovieDto> search(Integer startYear, Integer endYear, String genre);

    Integer saveAndConsumeDuplicate(InputStream inputStream);
}
