package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.entity.PsnTitle;
import fr.trophyquest.web.service.entity.projections.PlayedGameProjection;
import fr.trophyquest.web.service.mapper.GameDTOMapper;
import fr.trophyquest.web.service.repository.PsnTitleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameService {

    private final Logger logger = LoggerFactory.getLogger(GameService.class);

    private final PsnTitleRepository psnTitleRepository;
    private final GameDTOMapper gameMapper;

    public GameService(
            PsnTitleRepository psnTitleRepository,
            GameDTOMapper gameMapper
    ) {
        this.psnTitleRepository = psnTitleRepository;
        this.gameMapper = gameMapper;
    }

    public List<GameDTO> searchGames(
            String searchFrom,
            String searchSize,
            String sortBy,
            String sortDirection
    ) {
        logger.info(
                "In searchGames: searchFrom={}, searchSize={}, sortBy={}, sortDirection={}",
                searchFrom,
                searchSize,
                sortBy,
                sortDirection
        );
        final int pageNumber = Integer.parseInt(searchFrom);
        final int pageSize = Integer.parseInt(searchSize);
        final Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        final PageRequest pageRequest = PageRequest.of(
                pageNumber,
                pageSize,
                sort
        );
        return this.psnTitleRepository.findAll(pageRequest).stream().map(this.gameMapper::toDTO).toList();
    }

    public GameDTO getGameById(UUID gameId) {
        PsnTitle gameEntity = this.psnTitleRepository.findById(gameId).orElseThrow();
        return this.gameMapper.toDTO(gameEntity);
    }

    public List<PlayedGameProjection> getUserPlayedGames(
            UUID userId,
            String searchFrom,
            String searchSize
    ) {
        final int pageNumber = Integer.parseInt(searchFrom);
        final int pageSize = Integer.parseInt(searchSize);
        return this.psnTitleRepository.findByUserId(
                userId,
                pageNumber,
                pageSize
        );
    }

}
