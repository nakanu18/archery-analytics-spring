package com.deveradev.archeryanalyticsspring;

import com.deveradev.archeryanalyticsspring.entity.Archer;
import com.deveradev.archeryanalyticsspring.service.ArcheryRestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class ArcheryRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbc;

    @Mock
    ArcheryRestService archeryRestService;

    @Value("${sql.script.create.archer}")
    private String sqlCreateArcher;

    @Value("${sql.script.delete.archer}")
    private String sqlDeleteArcher;

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    public void testGetArchers_Success() throws Exception {
        jdbc.execute(sqlCreateArcher);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/archers");
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Alex de Vera")));
    }

    @Test
    public void testGetArchersById_Success() throws Exception {
        jdbc.execute(sqlCreateArcher);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/archers/1");
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Alex de Vera")));
    }

    @Test
    public void testGetArchersById_InvalidId() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/archers/2");
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Archer #id not found: 2")));
    }

    @Test
    public void testAddArcher_Success() throws Exception {
        Archer newArcher = new Archer();
        newArcher.setName("Chris de Vera");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/archers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newArcher));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Chris de Vera")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rounds", Matchers.nullValue()));

    }

    @AfterEach
    public void afterEach() {
        jdbc.execute(sqlDeleteArcher);
    }
}
