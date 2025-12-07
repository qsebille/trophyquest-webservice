package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.PlayerCollectionDTO;
import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.entity.projections.PlayerCollectionProjection;
import fr.trophyquest.web.service.mappers.PlayerCollectionMapper;
import fr.trophyquest.web.service.repository.TrophyCollectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CollectionService {

    private final TrophyCollectionRepository trophyCollectionRepository;
    private final PlayerCollectionMapper playerCollectionMapper;

    public CollectionService(
            TrophyCollectionRepository trophyCollectionRepository,
            PlayerCollectionMapper playerCollectionMapper
    ) {
        this.trophyCollectionRepository = trophyCollectionRepository;
        this.playerCollectionMapper = playerCollectionMapper;
    }

    public SearchDTO<PlayerCollectionDTO> searchForPlayer(UUID playerId, int page, int size) {
        int offset = page * size;
        List<PlayerCollectionProjection> projections =
                this.trophyCollectionRepository.searchPlayerTrophyCollections(playerId, size, offset);
        long total = this.trophyCollectionRepository.getTotalPlayerTrophyCollections(playerId);

        return SearchDTO.<PlayerCollectionDTO>builder()
                .content(projections.stream().map(this.playerCollectionMapper::toDTO).toList())
                .total(total)
                .build();
    }

}
