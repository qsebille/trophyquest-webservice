package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.dto.TrophyDTO;
import fr.trophyquest.web.service.service.GameService;
import fr.trophyquest.web.service.service.TrophyService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;
    private final TrophyService trophyService;

    public GameController(
            GameService gameService,
            TrophyService trophyService
    ) {
        this.gameService = gameService;
        this.trophyService = trophyService;
    }

    @GetMapping
    public List<GameDTO> searchGames(
            @RequestParam(name = "searchFrom", defaultValue = "0") String searchFrom,
            @RequestParam(name = "searchSize", defaultValue = "50") String searchSize,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection
    ) {
        return this.gameService.searchGames(
                searchFrom,
                searchSize,
                sortBy,
                sortDirection
        );
    }

    @GetMapping("/{id}")
    public GameDTO getGameById(@PathVariable UUID id) {
        return this.gameService.getGameById(id);
    }

    @GetMapping("/{id}/trophies")
    public List<TrophyDTO> getTrophies(@PathVariable UUID id) {
        return this.trophyService.getGameTrophies(id);
    }

}
