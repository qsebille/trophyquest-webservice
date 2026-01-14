package fr.trophyquest.backend.api.controller;

import fr.trophyquest.backend.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TrophySetControllerIT extends IntegrationTestBase {

    private static final String BASE_PATH = "/api/trophy-set";

    @Autowired
    MockMvc mockMvc;

    @Test
    void should_search_trophy_set_with_candidates() throws Exception {
        runScript("/sql/clean-db.sql");
        runScript("/sql/trophy-sets/insert-trophy-sets-with-candidates.sql");

        mockMvc.perform(get(BASE_PATH + "/search/candidates?pageNumber=0&pageSize=50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("total").value(1))
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("content", hasSize(1)))
                .andExpect(jsonPath("content[0].trophySet.title").value("Outer Wilds"))
                .andExpect(jsonPath("content[0].mappingCandidates", hasSize(2)))
                .andExpect(jsonPath("content[0].mappingCandidates[0].name").value("Outer Wilds"))
                .andExpect(jsonPath("content[0].mappingCandidates[0].score").value(100))
                .andExpect(jsonPath("content[0].mappingCandidates[1].name").value("Outer Worlds"))
                .andExpect(jsonPath("content[0].mappingCandidates[1].score").value(90))
        ;
    }

    @Test
    void should_fetch_trophy_set_by_id() throws Exception {
        runScript("/sql/clean-db.sql");
        runScript("/sql/trophy-sets/insert-trophy-sets.sql");

        mockMvc.perform(get(BASE_PATH + "/00000001-0000-0000-0000-000000000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("Outer Wilds"))
        ;
    }

    @Test
    void should_count_trophy_sets() throws Exception {
        runScript("/sql/clean-db.sql");
        runScript("/sql/trophy-sets/insert-trophy-sets.sql");

        mockMvc.perform(get(BASE_PATH + "/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(2))
        ;
    }
}

