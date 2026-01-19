package fr.trophyquest.backend.api.controller;

import fr.trophyquest.backend.api.dto.trophy.EarnedTrophyDTO;
import fr.trophyquest.backend.api.dto.trophysuite.RecentTrophySuiteDTO;
import fr.trophyquest.backend.api.dto.trophysuite.TrophySuiteDTO;
import fr.trophyquest.backend.service.IgdbCandidateService;
import fr.trophyquest.backend.service.TrophySuiteService;
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
@RequestMapping("/api/trophy-set")
@CrossOrigin(origins = "*")
public class TrophySuiteController {

    private final TrophySuiteService trophySuiteService;
    private final IgdbCandidateService igdbCandidateService;

    public TrophySuiteController(
            TrophySuiteService trophySuiteService,
            IgdbCandidateService igdbCandidateService
    ) {
        this.trophySuiteService = trophySuiteService;
        this.igdbCandidateService = igdbCandidateService;
    }

    @GetMapping("/{trophySuiteId}")
    public TrophySuiteDTO retrieve(@PathVariable UUID trophySuiteId) {
        return this.trophySuiteService.retrieve(trophySuiteId);
    }

    @GetMapping("/{trophySuiteId}/trophies")
    public List<EarnedTrophyDTO> getTrophies(
            @PathVariable UUID trophySuiteId,
            @RequestParam(name = "playerId", required = false) Optional<UUID> playerId
    ) {
        return this.trophySuiteService.fetchEarnedTrophies(trophySuiteId, playerId);
    }

    @GetMapping("/count")
    public long count() {
        return this.trophySuiteService.count();
    }

    @GetMapping("/recent/count")
    public long countRecent() {
        return this.trophySuiteService.countRecent();
    }

    @GetMapping("/top-recent")
    public List<RecentTrophySuiteDTO> getRecent() {
        return this.trophySuiteService.fetchRecent();
    }

}