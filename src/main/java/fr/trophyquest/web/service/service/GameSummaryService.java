package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.GameSummaryDTO;
import fr.trophyquest.web.service.entity.projections.GameSummaryProjection;
import fr.trophyquest.web.service.mappers.GameSummaryMapper;
import fr.trophyquest.web.service.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameSummaryService {
    private final GameRepository gameRepository;
    private final GameSummaryMapper gameSummaryMapper;


    public GameSummaryService(GameRepository gameRepository, GameSummaryMapper gameSummaryMapper) {
        this.gameRepository = gameRepository;
        this.gameSummaryMapper = gameSummaryMapper;
    }

    public GameSummaryDTO retrieveGameSummary(UUID gameId) {
        GameSummaryProjection projection = this.gameRepository.getSummaryById(gameId);
        return this.gameSummaryMapper.toDTO(projection);
    }
}
