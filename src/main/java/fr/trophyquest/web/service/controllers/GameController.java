package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.dto.RecentlyPlayedGameDTO;
import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.service.GameService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
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

    @GetMapping("/recently-played")
    public List<RecentlyPlayedGameDTO> recentlyPlayedGames() {
        final int limit = 10;
        return this.gameService.fetchRecentlyPlayedGames(limit);
    }

    @GetMapping("/count")
    public long count() {
        return this.gameService.countGames();
    }

}
