package fr.trophyquest.backend.api.controller;

import fr.trophyquest.backend.api.dto.SearchDTO;
import fr.trophyquest.backend.api.dto.game.GameWithIgdbCandidatesDTO;
import fr.trophyquest.backend.service.GameService;
import fr.trophyquest.backend.service.IgdbCandidateService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;
    private final IgdbCandidateService igdbCandidateService;

    public GameController(GameService gameService, IgdbCandidateService igdbCandidateService) {
        this.gameService = gameService;
        this.igdbCandidateService = igdbCandidateService;
    }

    @GetMapping("/search/candidates")
    public SearchDTO<GameWithIgdbCandidatesDTO> searchCandidates(
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumberParam,
            @RequestParam(name = "pageSize", defaultValue = "50") String pageSizeParam
    ) {
        final int pageNumber = Integer.parseInt(pageNumberParam);
        final int pageSize = Integer.parseInt(pageSizeParam);
        return this.gameService.searchWithCandidates(pageNumber, pageSize);
    }

    @PostMapping("/{gameId}/candidate/{igdbGameId}/validate")
    public Boolean validateCandidate(@PathVariable UUID gameId, @PathVariable long igdbGameId) {
        return this.igdbCandidateService.validateCandidate(gameId, igdbGameId);
    }

    @PutMapping("/{gameId}/candidate/reject-all")
    public Boolean rejectAllCandidates(@PathVariable UUID gameId) {
        return this.igdbCandidateService.rejectAllPendingCandidates(gameId);
    }

    @GetMapping("/count")
    public long count() {
        return this.gameService.count();
    }

}