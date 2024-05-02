package com.deveradev.archeryanalyticsspring;

import com.deveradev.archeryanalyticsspring.entity.Archer;
import com.deveradev.archeryanalyticsspring.entity.RoundDTO;
import com.deveradev.archeryanalyticsspring.rest.BadRequestException;
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

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional(rollbackFor = Exception.class) // Rollback on any exceptions
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

    @Value("${sql.script.add.round1}")
    private String sqlAddRound1;

    @Value("${sql.script.add.round2}")
    private String sqlAddRound2;

    @Value("${sql.script.clear.archers}")
    private String sqlClearArchers;

    @Value("${sql.script.clear.rounds}")
    private String sqlClearRounds;

    @BeforeEach
    public void beforeEach() {
        // Adding delete to beforeEach to prevent clash on adding a
        // new archer and then afterEach immediately trying to delete
        jdbc.execute(sqlClearArchers);
        jdbc.execute(sqlClearRounds);
    }

    private void debugArcherTable(String tableName) {
        List<Map<String, Object>> table = jdbc.queryForList("SELECT * FROM " + tableName);

        System.out.println("*** Debugging Table Data: " + tableName);
        for (Map<String, Object> tableEntry : table) {
            System.out.println("    " + tableEntry); // Print each archer record
        }
    }

    //
    // Archers
    //

    @Test
    public void testGetAllArchers_Success() throws Exception {
        jdbc.execute(sqlCreateArcher);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/archers");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Alex de Vera")));
    }

    @Test
    public void testGetArcherById_Success() throws Exception {
        jdbc.execute(sqlCreateArcher);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/archers/1");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Alex de Vera")));
    }

    @Test
    public void testGetArchersById_Failure_InvalidId() throws Exception {
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

    @Test
    public void testAddAdditionalArcher_Success() throws Exception {
        jdbc.execute(sqlCreateArcher);
        Archer newArcher = new Archer();
        newArcher.setName("Chris de Vera");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/archers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newArcher));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Chris de Vera")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rounds", Matchers.nullValue()));
    }

    @Test
    public void testDeleteArcher_Success_NoRounds() throws Exception {
        jdbc.execute(sqlCreateArcher);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/archers/1");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteArcher_Success_SomeRounds() throws Exception {
        jdbc.execute(sqlCreateArcher);
        jdbc.execute(sqlAddRound1);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/archers/1");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteArcher_Failure_InvalidId() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/archers/2");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Archer #id not found: 2")));
    }

    //
    // Rounds
    //

    @Test
    public void testAddRoundForArcher_Success() throws Exception {
        jdbc.execute(sqlCreateArcher);

        RoundDTO newRound = new RoundDTO();
        newRound.date = "2024-04-24 10:10am";
        newRound.archerId = 1;
        newRound.numEnds = 10;
        newRound.numArrowsPerEnd = 3;
        newRound.distM = 50;
        newRound.targetSizeCM = 122;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/rounds/archer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRound));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    public void testAddRoundForArcher_Failure_InvalidArcheryId() throws Exception {
        jdbc.execute(sqlCreateArcher);

        RoundDTO newRound = new RoundDTO();
        newRound.date = "2024-04-24 10:10am";
        newRound.archerId = 100;
        newRound.numEnds = 10;
        newRound.numArrowsPerEnd = 3;
        newRound.distM = 50;
        newRound.targetSizeCM = 122;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/rounds/archer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRound));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Archer #id not found: 100")));
    }

    @Test
    public void testAddRoundForArcher_Failure_InvalidDate() throws Exception {
        jdbc.execute(sqlCreateArcher);

        RoundDTO newRound = new RoundDTO();
        newRound.date = "24-04-24 10:10";
        newRound.archerId = 1;
        newRound.numEnds = 10;
        newRound.numArrowsPerEnd = 3;
        newRound.distM = 50;
        newRound.targetSizeCM = 122;

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/rounds/archer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRound));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> {
                    Throwable exception = result.getResolvedException();
                    assertThat(exception, instanceOf(BadRequestException.class));
                    assertThat(exception.getMessage(), containsString("Invalid date format"));
                });
    }

    @Test
    public void testDeleteRound_Success() throws Exception {
        jdbc.execute(sqlCreateArcher);
        jdbc.execute(sqlAddRound1);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/rounds/1");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteRound_Failure_InvalidId() throws Exception {
        jdbc.execute(sqlCreateArcher);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/rounds/100");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Round #id not found: 100")));
    }

    @Test
    public void testFindAllRoundsForArcherId_Success_NoRounds() throws Exception {
        jdbc.execute(sqlCreateArcher);

        String expectedBody = "[]";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/rounds/archer/1");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedBody));
    }

    @Test
    public void testFindAllRoundsForArcherId_Success_MultipleRounds() throws Exception {
        jdbc.execute(sqlCreateArcher);
        jdbc.execute(sqlAddRound1);
        jdbc.execute(sqlAddRound2);

        String expectedBody = "[{" +
                "\"id\":1," +
                "\"date\":\"2024-01-01T16:10:00.000+00:00\"," +
                "\"numEnds\":10," +
                "\"numArrowsPerEnd\":3," +
                "\"distM\":18," +
                "\"targetSizeCM\":40" +
                "},{" +
                "\"id\":2," +
                "\"date\":\"2024-04-01T15:10:00.000+00:00\"," +
                "\"numEnds\":10," +
                "\"numArrowsPerEnd\":3," +
                "\"distM\":18," +
                "\"targetSizeCM\":40" +
                "}]";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/rounds/archer/1");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(expectedBody));
    }

    @AfterEach
    public void afterEach() {
    }
}
