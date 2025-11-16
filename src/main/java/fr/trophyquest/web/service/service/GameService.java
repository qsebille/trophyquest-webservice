package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.entity.Game;
import fr.trophyquest.web.service.entity.PsnUser;
import fr.trophyquest.web.service.mapper.GameDTOMapper;
import fr.trophyquest.web.service.repository.GameRepository;
import fr.trophyquest.web.service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final GameDTOMapper gameMapper;
    private final Logger logger = LoggerFactory.getLogger(GameService.class);

    public GameService(GameRepository gameRepository, UserRepository userRepository, GameDTOMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.gameMapper = gameMapper;
    }

    public List<GameDTO> searchGames(String userId, String searchFrom, String searchSize, String sortBy, String sortDirection) {
        logger.info("In searchGames: userId={}, searchFrom={}, searchSize={}, sortBy={}, sortDirection={}", userId, searchFrom, searchSize, sortBy, sortDirection);
        final int pageNumber = Integer.parseInt(searchFrom);
        final int pageSize = Integer.parseInt(searchSize);
        final Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        if ("".equals(userId)) {
            return this.gameRepository.findAll(pageRequest).stream().map(this.gameMapper::toDTO).toList();
        } else {
            PsnUser user = this.userRepository.findById(UUID.fromString(userId)).orElseThrow();
            return this.gameRepository.findAllByPsnUsers(Set.of(user), pageRequest).stream().map(this.gameMapper::toDTO).toList();
        }
    }

    public GameDTO getGameById(UUID gameId) {
        Game gameEntity = this.gameRepository.findById(gameId).orElseThrow();
        return this.gameMapper.toDTO(gameEntity);
    }

}
