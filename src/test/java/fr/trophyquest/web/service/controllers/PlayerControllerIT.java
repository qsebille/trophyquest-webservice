package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(
        scripts = "/sql/clean-db.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class PlayerControllerIT extends IntegrationTestBase {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql(scripts = "/sql/players/player-controller-it-data.sql")
    void should_return_players_with_earned_trophies() throws Exception {
        mockMvc.perform(get("/api/player/search?pageNumber=0&pageSize=50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("total").value(2))
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("content", hasSize(2)))
                .andExpect(jsonPath("content[0].player.pseudo").value("PlayerTwo"))
                .andExpect(jsonPath("content[1].player.pseudo").value("PlayerOne"))
        ;
    }
}

