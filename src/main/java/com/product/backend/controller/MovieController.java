package com.product.backend.controller;

import com.product.backend.bo.MovieResponse;
import com.product.backend.bo.ResponseMessage;
import com.product.backend.dto.MovieDto;
import com.product.backend.helper.CSVHelper;
import com.product.backend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<MovieResponse> search(@RequestParam Integer startYear,
                                      @RequestParam(defaultValue = "2016") Integer endYear,
                                      @RequestParam(defaultValue = "no genre") String genre) {
        List<MovieDto> movieDtos = movieService.search(startYear, endYear, genre);
        return MovieResponse.convertToBo(movieDtos);
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message;

        if (Boolean.FALSE.equals(CSVHelper.hasCSVFormat(file))) {
            message = "Please upload a csv file!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
        try {
            message = "Movies Added : " + movieService.saveAndConsumeDuplicate(file.getInputStream());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

}
