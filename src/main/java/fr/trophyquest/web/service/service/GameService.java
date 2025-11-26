package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.dto.UserGameDTO;
import fr.trophyquest.web.service.entity.Game;
import fr.trophyquest.web.service.entity.TrophyCollection;
import fr.trophyquest.web.service.entity.projections.UserGameProjection;
import fr.trophyquest.web.service.mappers.GameMapper;
import fr.trophyquest.web.service.mappers.UserGameMapper;
import fr.trophyquest.web.service.repository.GameRepository;
import fr.trophyquest.web.service.repository.TrophyCollectionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final TrophyCollectionRepository trophyCollectionRepository;
    private final UserGameMapper userGameMapper;

    public GameService(
            GameRepository gameRepository,
            GameMapper gameMapper,
            TrophyCollectionRepository trophyCollectionRepository,
            UserGameMapper userGameMapper
    ) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        this.trophyCollectionRepository = trophyCollectionRepository;
        this.userGameMapper = userGameMapper;
    }


    /**
     * Searches for and retrieves a paginated list of games associated with a specific user.
     * This method gathers user game details and corresponding trophy collections.
     *
     * @param userId     the unique identifier of the user for whom the games are queried
     * @param pageNumber the page number for pagination, starting at 0
     * @param pageSize   the number of items per page for pagination
     * @return a UserGameSearchDTO containing the list of user games and the total number of games
     */
    public SearchDTO<UserGameDTO> searchUserGames(UUID userId, int pageNumber, int pageSize) {
        List<UserGameProjection> userGameProjections = this.gameRepository.searchByUserId(userId, pageNumber, pageSize);
        Set<UUID> gameIds = userGameProjections.stream().map(UserGameProjection::getId).collect(Collectors.toSet());
        List<TrophyCollection> trophyCollections = this.trophyCollectionRepository.findByUserAndGames(userId, gameIds);
        Map<UUID, Set<TrophyCollection>> gameCollectionsMap = new HashMap<>();
        trophyCollections.forEach(collection -> {
            UUID gameId = collection.getGame().getId();
            if (gameCollectionsMap.containsKey(gameId)) {
                Set<TrophyCollection> existingTrophyCollections = new HashSet<>(gameCollectionsMap.get(gameId));
                existingTrophyCollections.add(collection);
                gameCollectionsMap.put(gameId, existingTrophyCollections);
            } else {
                gameCollectionsMap.put(gameId, Set.of(collection));
            }
        });
        List<UserGameDTO> userGames = this.userGameMapper.toDtos(userGameProjections, gameCollectionsMap);

        long totalElements = this.gameRepository.countDistinctByUserId(userId);

        return new SearchDTO<>(userGames, totalElements);
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
     * Retrieves a game by its unique identifier and maps it to a GameDTO.
     *
     * @param gameId the unique identifier of the game to retrieve
     * @return a GameDTO containing the details of the game
     */
    public GameDTO getGame(UUID gameId) {
        Game game = this.gameRepository.findById(gameId).orElseThrow();
        return gameMapper.toDTO(game);
    }

}
