package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.entity.Game;
import fr.trophyquest.web.service.mappers.GameMapper;
import fr.trophyquest.web.service.repository.GameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public GameService(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    /**
     * Searches for a paginated list of games and returns relevant game information.
     *
     * @param pageNumber the page number for pagination, starting at 0
     * @param pageSize   the number of items per page for pagination
     * @return a GameSearchDTO containing the list of games and the total number of games
     */
    public SearchDTO<GameDTO> search(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "title"));
        Page<Game> games = this.gameRepository.findAll(pageRequest);

        return new SearchDTO<>(games.stream().map(this.gameMapper::toDTO).toList(), games.getTotalElements());
    }

    /**
     * Counts the total number of games played by a specific user.
     *
     * @param playerId the unique identifier of the player whose played games are being counted
     * @return the total number of games played by the specified player
     */
    public long countPlayedGamesByUser(UUID playerId) {
        return this.gameRepository.getTotalPlayerGamesPlayed(playerId);
    }
}
