package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IgdbCandidateControllerIT extends IntegrationTestBase {

    @Autowired
    MockMvc mockMvc;

    @Test
    void should_return_recently_most_popular_games() throws Exception {
        runScript("/sql/clean-db.sql");
        runScript("/sql/candidates/insert-games-with-candidates.sql");

        mockMvc.perform(get("/api/igdb-candidate/search?pageNumber=0&pageSize=50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].gameTitle").value("Tomb Raider"))
                .andExpect(jsonPath("$[0].candidates", hasSize(3)))
                .andExpect(jsonPath("$[0].candidates[0].name").value("Tomb Raider 1"))
                .andExpect(jsonPath("$[0].candidates[0].score").value(100))
                .andExpect(jsonPath("$[0].candidates[1].name").value("Tomb Raider 2"))
                .andExpect(jsonPath("$[0].candidates[1].score").value(90))
                .andExpect(jsonPath("$[0].candidates[2].name").value("Tomb Raider 3"))
                .andExpect(jsonPath("$[0].candidates[2].score").value(90))
                .andExpect(jsonPath("$[1].gameTitle").value("Outer Wilds"))
                .andExpect(jsonPath("$[1].candidates", hasSize(1)))
                .andExpect(jsonPath("$[1].candidates[0].name").value("Outer Wilds"))
                .andExpect(jsonPath("$[1].candidates[0].score").value(100))
        ;
    }
}

