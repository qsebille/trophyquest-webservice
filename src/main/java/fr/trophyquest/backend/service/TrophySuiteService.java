package fr.trophyquest.backend.service;

import fr.trophyquest.backend.api.dto.trophy.EarnedTrophyDTO;
import fr.trophyquest.backend.api.dto.trophysuite.RecentTrophySuiteDTO;
import fr.trophyquest.backend.api.dto.trophysuite.TrophySuiteDTO;
import fr.trophyquest.backend.api.mapper.TrophySuiteMapper;
import fr.trophyquest.backend.repository.PlayedTrophySuiteRepository;
import fr.trophyquest.backend.repository.TrophyRepository;
import fr.trophyquest.backend.repository.TrophySuiteRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrophySuiteService {

    private final TrophySuiteRepository trophySuiteRepository;
    private final PlayedTrophySuiteRepository playedTrophySuiteRepository;
    private final TrophyRepository trophyRepository;

    private final TrophySuiteMapper trophySuiteMapper;

    public TrophySuiteService(
            TrophySuiteRepository trophySuiteRepository,
            PlayedTrophySuiteRepository playedTrophySuiteRepository,
            TrophyRepository trophyRepository,
            TrophySuiteMapper trophySuiteMapper
    ) {
        this.trophySuiteRepository = trophySuiteRepository;
        this.playedTrophySuiteRepository = playedTrophySuiteRepository;
        this.trophyRepository = trophyRepository;
        this.trophySuiteMapper = trophySuiteMapper;
    }

    public TrophySuiteDTO retrieve(UUID trophySetId) {
        return this.trophySuiteMapper.toDTO(this.trophySuiteRepository.findById(trophySetId).orElseThrow());
    }


    public List<EarnedTrophyDTO> fetchEarnedTrophies(UUID trophySetId, Optional<UUID> playerId) {
        if (playerId.isEmpty()) {
            return this.trophyRepository.fetchTrophiesOfTrophySuite(trophySetId);
        } else {
            return this.trophyRepository.fetchPlayerTrophiesForTrophySet(trophySetId, playerId.get());
        }
    }

    public long count() {
        return this.trophySuiteRepository.count();
    }

    public long countRecent() {
        Instant limitDate = Instant.now().minus(7, ChronoUnit.DAYS);
        return this.playedTrophySuiteRepository.countRecentPlayedTrophySuites(limitDate);
    }

    public List<RecentTrophySuiteDTO> fetchRecent() {
        Instant limitDate = Instant.now().minus(7, ChronoUnit.DAYS);
        return this.playedTrophySuiteRepository.fetchRecentPlayedTrophySuites(limitDate);
    }

}
