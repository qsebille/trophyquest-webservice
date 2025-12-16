package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlayerControllerIT extends IntegrationTestBase {

    @Autowired
    MockMvc mockMvc;

    @Test
    void should_return_players_with_earned_trophies() throws Exception {
        runScript("/sql/clean-db.sql");
        runScript("/sql/players/player-controller-it-data.sql");

        mockMvc.perform(get("/api/player/search?pageNumber=0&pageSize=50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("total").value(2))
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("content", hasSize(2)))
                .andExpect(jsonPath("content[0].player.pseudo").value("PlayerTwo"))
                .andExpect(jsonPath("content[1].player.pseudo").value("PlayerOne"))
        ;
    }

    @Test
    void should_return_most_active_players() throws Exception {
        runScript("/sql/clean-db.sql");
        runScript("/sql/players/player-controller-it-most-active-data.sql");

        mockMvc.perform(get("/api/player/most-active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].player.pseudo").value("PlayerTwo"))
                .andExpect(jsonPath("$[0].recentTrophyCount").value(2))
                .andExpect(jsonPath("$[0].lastObtainedTrophies").exists())
                .andExpect(jsonPath("$[0].lastObtainedTrophies", hasSize(2)))
                .andExpect(jsonPath("$[1].player.pseudo").value("PlayerOne"))
                .andExpect(jsonPath("$[1].recentTrophyCount").value(1))
                .andExpect(jsonPath("$[1].lastObtainedTrophies").exists())
                .andExpect(jsonPath("$[1].lastObtainedTrophies", hasSize(1)))
        ;
    }
}

