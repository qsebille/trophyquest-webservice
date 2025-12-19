package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.dto.PlayerGameDTO;
import fr.trophyquest.web.service.dto.PopularGameDTO;
import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.entity.Game;
import fr.trophyquest.web.service.entity.projections.PlayerGameProjection;
import fr.trophyquest.web.service.entity.projections.PopularGameProjection;
import fr.trophyquest.web.service.mappers.GameMapper;
import fr.trophyquest.web.service.mappers.PlayerGameMapper;
import fr.trophyquest.web.service.mappers.PopularGameMapper;
import fr.trophyquest.web.service.repository.GameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final PlayerGameMapper playerGameMapper;
    private final PopularGameMapper popularGameMapper;

    public GameService(
            GameRepository gameRepository, GameMapper gameMapper, PlayerGameMapper playerGameMapper,
            PopularGameMapper popularGameMapper
    ) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        this.playerGameMapper = playerGameMapper;
        this.popularGameMapper = popularGameMapper;
    }

    public SearchDTO<GameDTO> search(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "title"));
        Page<Game> games = this.gameRepository.findAll(pageRequest);

        return SearchDTO.<GameDTO>builder()
                .content(games.stream().map(this.gameMapper::toDTO).toList())
                .total(games.getTotalElements())
                .build();
    }

    public SearchDTO<PlayerGameDTO> searchGamesForUser(UUID playerId, int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        List<PlayerGameProjection> projections = this.gameRepository.searchGamesForPlayer(playerId, pageSize, offset);
        long totalPlayedGames = this.gameRepository.getTotalPlayedGamesForPlayer(playerId);

        return SearchDTO.<PlayerGameDTO>builder()
                .content(projections.stream().map(this.playerGameMapper::toDTO).toList())
                .total(totalPlayedGames)
                .build();
    }

    public long countPlayedGamesByUser(UUID playerId) {
        return this.gameRepository.getTotalPlayedGamesForPlayer(playerId);
    }

    public List<PopularGameDTO> fetchMostPopularGames(int limit) {
        List<PopularGameProjection> projections = this.gameRepository.fetchRecentlyPlayedGames(limit);
        return projections.stream().map(this.popularGameMapper::toDTO).toList();
    }

    public long countGames() {
        return this.gameRepository.count();
    }

}
