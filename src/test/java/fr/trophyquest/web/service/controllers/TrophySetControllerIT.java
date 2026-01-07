package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TrophySetControllerIT extends IntegrationTestBase {

    @Autowired
    MockMvc mockMvc;

    @Test
    void should_return_trophy_set_with_candidates() throws Exception {
        runScript("/sql/clean-db.sql");
        runScript("/sql/trophy-sets/insert-trophy-sets-with-candidates.sql");

        mockMvc.perform(get("/api/trophy-set/search?pageNumber=0&pageSize=50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("total").value(2))
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("content", hasSize(2)))
                .andExpect(jsonPath("content[0].title").value("Tomb Raider"))
                .andExpect(jsonPath("content[0].igdbGameCandidates", hasSize(3)))
                .andExpect(jsonPath("content[0].igdbGameCandidates[0].name").value("Tomb Raider 1"))
                .andExpect(jsonPath("content[0].igdbGameCandidates[0].score").value(100))
                .andExpect(jsonPath("content[0].igdbGameCandidates[1].name").value("Tomb Raider 2"))
                .andExpect(jsonPath("content[0].igdbGameCandidates[1].score").value(90))
                .andExpect(jsonPath("content[0].igdbGameCandidates[2].name").value("Tomb Raider 3"))
                .andExpect(jsonPath("content[0].igdbGameCandidates[2].score").value(80))
                .andExpect(jsonPath("content[1].title").value("Outer Wilds"))
                .andExpect(jsonPath("content[1].igdbGameCandidates", hasSize(1)))
                .andExpect(jsonPath("content[1].igdbGameCandidates[0].name").value("Outer Wilds"))
                .andExpect(jsonPath("content[1].igdbGameCandidates[0].score").value(100))
        ;
    }
}

