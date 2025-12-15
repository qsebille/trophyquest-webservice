package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlayerControllerIT extends IntegrationTestBase {

    @Autowired
    MockMvc mockMvc;

    @Test
    void should_return_players_with_stats() throws Exception {
        mockMvc.perform(get("/api/player"))
                .andExpect(status().isOk());
    }
}

