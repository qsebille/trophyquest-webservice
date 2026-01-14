package fr.trophyquest.backend.service;

import fr.trophyquest.backend.api.dto.SearchDTO;
import fr.trophyquest.backend.api.dto.trophy.EarnedTrophyDTO;
import fr.trophyquest.backend.api.dto.trophyset.RecentTrophySetDTO;
import fr.trophyquest.backend.api.dto.trophyset.TrophySetDTO;
import fr.trophyquest.backend.api.dto.trophyset.TrophySetWithCandidatesDTO;
import fr.trophyquest.backend.api.mapper.TrophySetMapper;
import fr.trophyquest.backend.domain.entity.TrophySet;
import fr.trophyquest.backend.repository.PlayedTrophySetRepository;
import fr.trophyquest.backend.repository.TrophyRepository;
import fr.trophyquest.backend.repository.TrophySetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrophySetService {

    private final TrophySetRepository trophySetRepository;
    private final PlayedTrophySetRepository playedTrophySetRepository;
    private final TrophyRepository trophyRepository;

    private final TrophySetMapper trophySetMapper;

    public TrophySetService(
            TrophySetRepository trophySetRepository,
            PlayedTrophySetRepository playedTrophySetRepository,
            TrophyRepository trophyRepository,
            TrophySetMapper trophySetMapper
    ) {
        this.trophySetRepository = trophySetRepository;
        this.playedTrophySetRepository = playedTrophySetRepository;
        this.trophyRepository = trophyRepository;
        this.trophySetMapper = trophySetMapper;
    }

    public TrophySetDTO retrieveTrophySet(UUID trophySetId) {
        return this.trophySetMapper.toDTO(this.trophySetRepository.findById(trophySetId).orElseThrow());
    }

    public SearchDTO<TrophySetWithCandidatesDTO> searchWithCandidates(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<UUID> gameUuids = this.trophySetRepository.findTrophySetIdsHavingPendingCandidate(pageRequest);
        List<TrophySet> games = this.trophySetRepository.fetchTrophySetsWithCandidatesByIds(gameUuids.getContent());

        List<TrophySetWithCandidatesDTO> trophySets = games.stream().map(
                this.trophySetMapper::toWithCandidatesDTO).toList();
        long total = gameUuids.getTotalElements();

        return SearchDTO.<TrophySetWithCandidatesDTO>builder().content(trophySets).total(total).build();
    }

    public List<EarnedTrophyDTO> fetchEarnedTrophies(UUID trophySetId, Optional<UUID> playerId) {
        if (playerId.isEmpty()) {
            return this.trophyRepository.fetchTrophiesOfTrophySet(trophySetId);
        } else {
            return this.trophyRepository.fetchPlayerTrophiesForTrophySet(trophySetId, playerId.get());
        }
    }

    public long count() {
        return this.trophySetRepository.count();
    }

    public long countRecent() {
        Instant limitDate = Instant.now().minus(7, ChronoUnit.DAYS);
        return this.playedTrophySetRepository.countRecentTrophySets(limitDate);
    }

    public List<RecentTrophySetDTO> fetchRecent() {
        Instant limitDate = Instant.now().minus(7, ChronoUnit.DAYS);
        return this.playedTrophySetRepository.fetchRecentTrophySets(limitDate).stream().map(
                this.trophySetMapper::toRecent).toList();
    }

}
