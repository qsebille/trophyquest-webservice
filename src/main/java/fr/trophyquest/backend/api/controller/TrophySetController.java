package fr.trophyquest.backend.api.controller;

import fr.trophyquest.backend.api.dto.SearchDTO;
import fr.trophyquest.backend.api.dto.trophy.EarnedTrophyDTO;
import fr.trophyquest.backend.api.dto.trophyset.RecentTrophySetDTO;
import fr.trophyquest.backend.api.dto.trophyset.TrophySetDTO;
import fr.trophyquest.backend.api.dto.trophyset.TrophySetWithCandidatesDTO;
import fr.trophyquest.backend.service.TrophySetService;
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
public class TrophySetController {

    private final TrophySetService trophySetService;

    public TrophySetController(TrophySetService trophySetService) {
        this.trophySetService = trophySetService;
    }

    @GetMapping("/search/candidates")
    public SearchDTO<TrophySetWithCandidatesDTO> searchCandidates(
            @RequestParam(name = "pageNumber", defaultValue = "0") String pageNumberParam,
            @RequestParam(name = "pageSize", defaultValue = "50") String pageSizeParam
    ) {
        final int pageNumber = Integer.parseInt(pageNumberParam);
        final int pageSize = Integer.parseInt(pageSizeParam);
        return this.trophySetService.searchWithCandidates(pageNumber, pageSize);
    }

    @GetMapping("/{trophySetId}")
    public TrophySetDTO retrieve(@PathVariable UUID trophySetId) {
        return this.trophySetService.retrieveTrophySet(trophySetId);
    }

    @GetMapping("/{trophySetId}/trophies")
    public List<EarnedTrophyDTO> getTrophySetById(
            @PathVariable UUID trophySetId,
            @RequestParam(name = "playerId", required = false) Optional<UUID> playerId
    ) {
        return this.trophySetService.fetchEarnedTrophies(trophySetId, playerId);
    }

    @GetMapping("/count")
    public long count() {
        return this.trophySetService.count();
    }

    @GetMapping("/recent/count")
    public long countRecent() {
        return this.trophySetService.countRecent();
    }

    @GetMapping("/top-recent")
    public List<RecentTrophySetDTO> getRecent() {
        return this.trophySetService.fetchRecent();
    }
}