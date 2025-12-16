package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.MostActivePlayerResponseDTO;
import fr.trophyquest.web.service.dto.PlayerCollectionDTO;
import fr.trophyquest.web.service.dto.PlayerDTO;
import fr.trophyquest.web.service.dto.PlayerSummaryDTO;
import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.dto.TrophyCountDTO;
import fr.trophyquest.web.service.dto.TrophyDTO;
import fr.trophyquest.web.service.service.CollectionService;
import fr.trophyquest.web.service.service.GameService;
import fr.trophyquest.web.service.service.PlayerService;
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
@RequestMapping("/api/player")
@CrossOrigin(origins = "*")
public class PlayerController {

    private final PlayerService playerService;
    private final GameService gameService;
    private final CollectionService collectionService;
    private final TrophyService trophyService;

    public PlayerController(
            PlayerService playerService,
            GameService gameService,
            CollectionService collectionService,
            TrophyService trophyService
    ) {
        this.playerService = playerService;
        this.gameService = gameService;
        this.collectionService = collectionService;
        this.trophyService = trophyService;
    }


    @GetMapping("/search")
    public SearchDTO<PlayerSummaryDTO> search(
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumberParam,
            @RequestParam(name = "pageSize", defaultValue = "50") String pageSizeParam
    ) {
        final int pageNumber = Integer.parseInt(pageNumberParam);
        final int pageSize = Integer.parseInt(pageSizeParam);
        return this.playerService.search(pageNumber, pageSize);
    }

    @GetMapping("/{playerId}")
    public PlayerDTO fetch(@PathVariable UUID playerId) {
        return playerService.findById(playerId);
    }

    @GetMapping("/{playerId}/trophy/count")
    public TrophyCountDTO fetchTrophyCount(@PathVariable UUID playerId) {
        return this.trophyService.getPlayerTrophyCount(playerId);
    }

    @GetMapping("/{playerId}/collection/search")
    public SearchDTO<PlayerCollectionDTO> fetchGames(
            @PathVariable UUID playerId,
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumberParam,
            @RequestParam(name = "pageSize", defaultValue = "50") String pageSizeParam
    ) {
        final int pageNumber = Integer.parseInt(pageNumberParam);
        final int pageSize = Integer.parseInt(pageSizeParam);
        return this.collectionService.searchForPlayer(playerId, pageNumber, pageSize);
    }

    @GetMapping("/{playerId}/collection/{collectionId}/trophies")
    public List<TrophyDTO> fetchTrophiesOfCollection(
            @PathVariable UUID playerId,
            @PathVariable UUID collectionId
    ) {
        return this.trophyService.fetchPlayerCollectionTrophies(playerId, collectionId);
    }

    @GetMapping("/{playerId}/trophies")
    public SearchDTO<TrophyDTO> fetchTrophies(
            @PathVariable UUID playerId,
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumberParam,
            @RequestParam(name = "pageSize", defaultValue = "50") String pageSizeParam
    ) {
        final int pageNumber = Integer.parseInt(pageNumberParam);
        final int pageSize = Integer.parseInt(pageSizeParam);
        return this.trophyService.searchEarnedTrophiesByPlayer(playerId, pageNumber, pageSize);
    }

    @GetMapping("/count")
    public int count() {
        return this.playerService.count();
    }

    @GetMapping("/{playerId}/game/count")
    public long countPlayedGames(@PathVariable UUID playerId) {
        return this.gameService.countPlayedGamesByUser(playerId);
    }

    @GetMapping("/most-active")
    public List<MostActivePlayerResponseDTO> mostActivePlayers(
    ) {
        final int limit = 10;
        return this.playerService.fetchMostActive(limit);
    }

}
