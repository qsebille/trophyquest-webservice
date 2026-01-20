package fr.trophyquest.backend.service;

import fr.trophyquest.backend.api.dto.SearchDTO;
import fr.trophyquest.backend.api.dto.game.GameWithIgdbCandidatesDTO;
import fr.trophyquest.backend.api.mapper.GameMapper;
import fr.trophyquest.backend.domain.entity.Game;
import fr.trophyquest.backend.repository.GameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public GameService(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    public SearchDTO<GameWithIgdbCandidatesDTO> searchWithCandidates(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<UUID> gameUuids = this.gameRepository.findGameIdsHavingPendingCandidate(pageRequest);
        List<Game> games = this.gameRepository.fetchGamesWithCandidatesByIds(gameUuids.getContent());

        List<GameWithIgdbCandidatesDTO> gamesWithCandidates = games.stream()
                .map(this.gameMapper::toWithCandidatesDTO)
                .toList();
        long total = gameUuids.getTotalElements();

        return SearchDTO.<GameWithIgdbCandidatesDTO>builder().content(gamesWithCandidates).total(total).build();
    }

    public long count() {
        return this.gameRepository.count();
    }

    public long countRecentlyPlayed() {
        Instant limitDate = Instant.now().minus(7, ChronoUnit.DAYS);
        return this.gameRepository.countRecentlyPlayed(limitDate);
    }

}
