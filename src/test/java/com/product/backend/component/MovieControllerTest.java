package com.product.backend.component;

import com.product.backend.BackendApplicationTests;
import com.product.backend.helper.CSVHelper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieControllerTest extends BackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void componentTest_search_valid_success() throws Exception {
        String output = "[{\"id\":2,\"rank\":2,\"title\":\"Movie2013\",\"genre\":\"no genre\",\"year\":2013,\"runTime\":\"12\",\"rating\":3.0,\"description\":\"aaaa\"}]";
        mockMvc.perform(get("/movie?startYear=2012")).andExpect(status().isOk()).andExpect(content().string(output));
    }

    @Test
    @Order(2)
    public void componentTest_search_genre_success() throws Exception {
        String output = "[{\"id\":3,\"rank\":2,\"title\":\"Movie2014\",\"genre\":\"Cartoon\",\"year\":2014,\"runTime\":\"1hr\",\"rating\":4.0,\"description\":\"Nice\"}]";
        mockMvc.perform(get("/movie?startYear=2012&genre=Cartoon"))
                .andExpect(status().isOk()).andExpect(content().string(output));
    }

    @Test
    @Order(3)
    public void componentTest_search_endYear_success() throws Exception {
        String output = "[{\"id\":2,\"rank\":2,\"title\":\"Movie2013\",\"genre\":\"no genre\",\"year\":2013,\"runTime\":\"12\",\"rating\":3.0,\"description\":\"aaaa\"}]";
        mockMvc.perform(get("/movie?startYear=2012&endYear=2015"))
                .andExpect(status().isOk()).andExpect(content().string(output));
    }

    @Test
    @Order(4)
    public void componentTest_search_startYearMissing_badRequest() throws Exception {
        mockMvc.perform(get("/movie")).andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    public void componentTest_uploadFile_valid_success() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File(CSV_FILE));
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",CSV_FILE, CSVHelper.TYPE, fileInputStream);
        mockMvc.perform(multipart("/movie").file(mockMultipartFile))
                .andExpect(status().isOk());
    }
    @Test
    @Order(6)
    public void componentTest_uploadFile_inValid_badRequest() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File(CSV_FILE));
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileInputStream);
        mockMvc.perform(multipart("/movie").file(mockMultipartFile))
                .andExpect(status().isBadRequest());
    }
    @Test
    @Order(7)
    public void componentTest_uploadFile_invalidFileData_zeroAdded() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", null, CSVHelper.TYPE, CSV_FILE.getBytes());
        mockMvc.perform(multipart("/movie").file(mockMultipartFile))
                .andExpect(content().string("{\"message\":\"Movies Added : 0\"}"))
                .andExpect(status().isOk());
    }
}
