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

        return SearchDTO.<GameDTO>builder()
                .content(games.stream().map(this.gameMapper::toDTO).toList())
                .total(games.getTotalElements())
                .build();
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

    /**
     * Fetches a list of recently played games based on the provided pagination parameters.
     *
     * @param pageNumber the number of the page to fetch, starting at 0
     * @param pageSize   the number of games to fetch per page
     * @return a list of GameDTO objects representing the recently played games
     */
    public List<GameDTO> fetchRecentlyPlayed(int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        List<Game> games = this.gameRepository.fetchRecentlyPlayedGames(pageSize, offset);
        return games.stream().map(this.gameMapper::toDTO).toList();
    }

}
