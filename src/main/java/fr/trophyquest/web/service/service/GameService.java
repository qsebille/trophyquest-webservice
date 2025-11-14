package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.mapper.GameDTOMapper;
import fr.trophyquest.web.service.entity.Game;
import fr.trophyquest.web.service.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameDTOMapper gameMapper;

    public GameService(GameRepository gameRepository, GameDTOMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    public List<GameDTO> findAll() {
        return this.gameRepository.findAll()
                .stream()
                .map(this.gameMapper::toDTO)
                .toList();
    }

    public GameDTO getGameById(UUID gameId) {
        Game gameEntity = this.gameRepository.findById(gameId).orElseThrow();
        return this.gameMapper.toDTO(gameEntity);
    }

}
