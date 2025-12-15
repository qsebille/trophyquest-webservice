package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GameControllerIT extends IntegrationTestBase {

    @Autowired
    MockMvc mockMvc;

    @Test
    void should_return_recently_most_played_games() throws Exception {
        runScript("/sql/clean-db.sql");
        runScript("/sql/games/game-controller-it-recently-played-endpoint-data.sql");

        mockMvc.perform(get("/api/game/recently-played?pageNumber=0&pageSize=50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("total").value(3))
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("content", hasSize(3)))
                .andExpect(jsonPath("content[0].title").value("Horizon"))
                .andExpect(jsonPath("content[1].title").value("Outer Wilds"))
                .andExpect(jsonPath("content[2].title").value("Tomb Raider"))
        ;
    }
}

