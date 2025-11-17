package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.GameSearchDTO;
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
    public GameSearchDTO searchGames(
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumberParam,
            @RequestParam(name = "pageSize", defaultValue = "50") String pageSizeParam
    ) {
        final int pageNumber = Integer.parseInt(pageNumberParam);
        final int pageSize = Integer.parseInt(pageSizeParam);
        return this.gameService.search(pageNumber, pageSize);
    }

    @GetMapping("/{id}/trophies")
    public List<TrophyDTO> getTrophies(@PathVariable UUID id) {
        return this.trophyService.getGameTrophies(id);
    }

}
