package fr.trophyquest.web.service.controllers;

import fr.trophyquest.web.service.dto.MostActivePlayerResponseDTO;
import fr.trophyquest.web.service.dto.PlayerDTO;
import fr.trophyquest.web.service.dto.PlayerGameDTO;
import fr.trophyquest.web.service.dto.PlayerSummaryDTO;
import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.dto.TrophyCountByTypeDto;
import fr.trophyquest.web.service.dto.TrophyDTO;
import fr.trophyquest.web.service.dto.responses.PlayerByPseudoResponse;
import fr.trophyquest.web.service.service.GameService;
import fr.trophyquest.web.service.service.PlayerService;
import fr.trophyquest.web.service.service.TrophyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/player")
@CrossOrigin(origins = "*")
public class PlayerController {
    private final PlayerService playerService;
    private final GameService gameService;
    private final TrophyService trophyService;

    public PlayerController(PlayerService playerService, GameService gameService, TrophyService trophyService) {
        this.playerService = playerService;
        this.gameService = gameService;
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

    @GetMapping("/pseudo/{profilePseudo}")
    public ResponseEntity<PlayerByPseudoResponse> fetchByPseudo(@PathVariable String profilePseudo) {
        Optional<PlayerDTO> foundPlayer = this.playerService.findByPseudo(profilePseudo);
        PlayerByPseudoResponse body;
        if (foundPlayer.isEmpty()) {
            body = PlayerByPseudoResponse.builder()
                    .status("NOT_FOUND")
                    .build();
        } else {
            body = PlayerByPseudoResponse.builder()
                    .status("FOUND")
                    .player(foundPlayer.get())
                    .build();
        }
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{playerId}")
    public PlayerDTO fetch(@PathVariable UUID playerId) {
        return this.playerService.findById(playerId);
    }

    @GetMapping("/{playerId}/trophy/count")
    public TrophyCountByTypeDto fetchTrophyCount(@PathVariable UUID playerId) {
        return this.trophyService.getPlayerTrophyCount(playerId);
    }

    @GetMapping("/{playerId}/game/search")
    public SearchDTO<PlayerGameDTO> fetchGames(
            @PathVariable UUID playerId,
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumberParam,
            @RequestParam(name = "pageSize", defaultValue = "50") String pageSizeParam
    ) {
        final int pageNumber = Integer.parseInt(pageNumberParam);
        final int pageSize = Integer.parseInt(pageSizeParam);
        return this.gameService.searchGamesForUser(playerId, pageNumber, pageSize);
    }

    @GetMapping("/{playerId}/game/{gameId}/trophies")
    public List<TrophyDTO> fetchTrophiesOfGame(@PathVariable UUID playerId, @PathVariable UUID gameId) {
        return this.trophyService.fetchPlayerTrophiesForGame(playerId, gameId);
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
