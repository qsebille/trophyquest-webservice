package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.dto.GameSearchDTO;
import fr.trophyquest.web.service.entity.Game;
import fr.trophyquest.web.service.mappers.GameMapper;
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

    public GameService(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    public GameSearchDTO searchUserGames(UUID userId, int pageNumber, int pageSize) {
        List<Game> games = this.gameRepository.searchByUserId(userId, pageNumber, pageSize);
        long totalElements = this.gameRepository.countDistinctByUserId(userId);

        return new GameSearchDTO(games.stream().map(this.gameMapper::toDTO).toList(), totalElements);
    }

    public GameSearchDTO search(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "title"));
        Page<Game> games = this.gameRepository.findAll(pageRequest);

        return new GameSearchDTO(games.stream().map(this.gameMapper::toDTO).toList(), games.getTotalElements());
    }

    public GameDTO getGame(UUID gameId) {
        Game game = this.gameRepository.findById(gameId).orElseThrow();
        return gameMapper.toDTO(game);
    }

}
