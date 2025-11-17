package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.dto.GameSearchDTO;
import fr.trophyquest.web.service.entity.projections.GameProjection;
import fr.trophyquest.web.service.entity.projections.PlayedGameProjection;
import fr.trophyquest.web.service.mapper.GameDTOMapper;
import fr.trophyquest.web.service.repository.PsnTitleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameService {
    private final PsnTitleRepository psnTitleRepository;
    private final GameDTOMapper gameDTOMapper;

    public GameService(
            PsnTitleRepository psnTitleRepository,
            GameDTOMapper gameDTOMapper
    ) {
        this.psnTitleRepository = psnTitleRepository;
        this.gameDTOMapper = gameDTOMapper;
    }

    public GameSearchDTO search(
            int pageNumber,
            int pageSize
    ) {
        List<GameProjection> projections = this.psnTitleRepository.search(pageNumber, pageSize);
        List<GameDTO> games = this.gameDTOMapper.buildGames(projections);
        long total = this.psnTitleRepository.count();

        return new GameSearchDTO(games, total);
    }

    public List<PlayedGameProjection> getUserPlayedGames(
            UUID userId,
            String searchFrom,
            String searchSize
    ) {
        final int pageNumber = Integer.parseInt(searchFrom);
        final int pageSize = Integer.parseInt(searchSize);
        return this.psnTitleRepository.findByUserId(userId, pageNumber, pageSize);
    }

}
