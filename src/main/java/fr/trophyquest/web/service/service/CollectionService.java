package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.CollectionDTO;
import fr.trophyquest.web.service.dto.PlayerCollectionDTO;
import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.entity.projections.CollectionProjection;
import fr.trophyquest.web.service.entity.projections.PlayerCollectionProjection;
import fr.trophyquest.web.service.mappers.CollectionMapper;
import fr.trophyquest.web.service.mappers.PlayerCollectionMapper;
import fr.trophyquest.web.service.repository.TrophyCollectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CollectionService {

    private final TrophyCollectionRepository trophyCollectionRepository;

    private final CollectionMapper collectionMapper;
    private final PlayerCollectionMapper playerCollectionMapper;

    public CollectionService(
            TrophyCollectionRepository trophyCollectionRepository,
            CollectionMapper collectionMapper,
            PlayerCollectionMapper playerCollectionMapper
    ) {
        this.trophyCollectionRepository = trophyCollectionRepository;
        this.collectionMapper = collectionMapper;
        this.playerCollectionMapper = playerCollectionMapper;
    }

    /**
     * Searches for player trophy collections based on the provided player ID, page number, and page size.
     * Returns a paginated result containing player trophy collection data.
     *
     * @param playerId the unique identifier of the player whose trophy collections are being queried
     * @param page     the page number to retrieve (0-indexed)
     * @param size     the number of items per page
     * @return a SearchDTO containing the list of PlayerCollectionDTOs representing the player's trophy collections and the total count of collections
     */
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

    public CollectionDTO retrieve(UUID id) {
        CollectionProjection projection = this.trophyCollectionRepository.retrieveCollection(id);
        return this.collectionMapper.toDTO(projection);
    }

}
