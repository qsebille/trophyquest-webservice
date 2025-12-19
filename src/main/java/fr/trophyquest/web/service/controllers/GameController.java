package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.dto.GameSummaryDTO;
import fr.trophyquest.web.service.dto.PopularGameDTO;
import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.service.GameService;
import fr.trophyquest.web.service.service.GameSummaryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;
    private final GameSummaryService gameSummaryService;

    public GameController(GameService gameService, GameSummaryService gameSummaryService) {
        this.gameService = gameService;
        this.gameSummaryService = gameSummaryService;
    }

    @GetMapping
    public SearchDTO<GameDTO> searchGames(
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumberParam,
            @RequestParam(name = "pageSize", defaultValue = "50") String pageSizeParam
    ) {
        final int pageNumber = Integer.parseInt(pageNumberParam);
        final int pageSize = Integer.parseInt(pageSizeParam);
        return this.gameService.search(pageNumber, pageSize);
    }

    @GetMapping("/most-popular")
    public List<PopularGameDTO> mostPopularGames() {
        final int limit = 10;
        return this.gameService.fetchMostPopularGames(limit);
    }

    @GetMapping("/count")
    public long count() {
        return this.gameService.countGames();
    }

    @GetMapping("/{gameId}/summary")
    public GameSummaryDTO gameSummary(@PathVariable UUID gameId) {
        return this.gameSummaryService.retrieveGameSummary(gameId);
    }

}
