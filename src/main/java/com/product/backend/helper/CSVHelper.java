package com.product.backend.helper;

import com.product.backend.model.Movie;
import com.product.backend.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CSVHelper {
    public static final String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Movie> csvToMovies(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Movie> movies = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Movie movie = new Movie();
                movie.setRank(Integer.parseInt(csvRecord.get("Rank").trim()));
                movie.setTitle(csvRecord.get("Title"));
                movie.setGenre(csvRecord.get("Genre"));
                User director = new User();
                director.setName(csvRecord.get("Director"));
                movie.setDirector(director);
                String actors = csvRecord.get("Actors");
                String[] actorNames = actors.split(",");
                Set<User> actorUsers = new HashSet<>();
                for (String name : actorNames) {
                    if (name.trim().length() != 0){
                        User user = new User();
                        user.setName(name);
                        actorUsers.add(user);
                    }
                }
                movie.setActors(actorUsers);
                movie.setDescription(csvRecord.get("Description"));
                movie.setYear(Integer.valueOf(csvRecord.get("Year").trim()));
                movie.setRunTime(csvRecord.get("RunTime"));
                movie.setRating(Double.valueOf(csvRecord.get("Rating").trim()));
                movies.add(movie);
            }

            return movies;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}