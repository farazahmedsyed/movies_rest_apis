package com.product.backend.helper;

import com.product.backend.BaseTest;
import com.product.backend.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CSVHelperTest extends BaseTest {

    @Test
    public void unitTest_hasCSVFormat_csv_true() {
        MultipartFile multipartFile = new MockMultipartFile(CSV_FILE,
                null,
                CSVHelper.TYPE,
                CSV_FILE.getBytes());
        assertTrue(CSVHelper.hasCSVFormat(multipartFile));
    }

    @Test
    public void unitTest_hasCSVFormat_notCSV_true() {
        MultipartFile multipartFile = new MockMultipartFile("xyz.xlsx", CSV_FILE.getBytes());
        assertFalse(CSVHelper.hasCSVFormat(multipartFile));
    }

    @Test
    public void unitTest_csvToMovies_valid_success() throws FileNotFoundException {
        final Movie expectedMovie1 = createMovie1();
        final Movie expectedMovie2 = createMovie2();
        File file = new File(CSV_FILE);
        FileInputStream fileInputStream = new FileInputStream(file);
        List<Movie> movies = CSVHelper.csvToMovies(fileInputStream);
        assertNotNull(movies);
        assertEquals(2, movies.size());
        assertEquals(expectedMovie1, movies.get(0));
        assertEquals(expectedMovie2, movies.get(1));
        assertNotEquals(expectedMovie2, movies.get(0));
        assertNotEquals(expectedMovie1, movies.get(1));
    }
}
