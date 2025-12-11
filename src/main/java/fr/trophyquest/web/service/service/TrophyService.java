package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.ObtainedTrophyDTO;
import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.dto.TrophyCountDTO;
import fr.trophyquest.web.service.dto.TrophyDTO;
import fr.trophyquest.web.service.entity.projections.TrophyCountProjection;
import fr.trophyquest.web.service.entity.projections.TrophyProjection;
import fr.trophyquest.web.service.mappers.TrophyCountMapper;
import fr.trophyquest.web.service.mappers.TrophyMapper;
import fr.trophyquest.web.service.repository.TrophyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrophyService {

    private final TrophyRepository trophyRepository;
    private final TrophyCountMapper trophyCountMapper;
    private final TrophyMapper trophyMapper;

    public TrophyService(
            TrophyRepository trophyRepository,
            TrophyCountMapper trophyCountMapper,
            TrophyMapper trophyMapper
    ) {
        this.trophyRepository = trophyRepository;
        this.trophyCountMapper = trophyCountMapper;
        this.trophyMapper = trophyMapper;
    }

    /**
     * Retrieves the trophy count for a specific player in the form of a TrophyCountDTO object.
     *
     * @param playerId the unique identifier of the player whose trophy count is being queried
     * @return a TrophyCountDTO containing the counts of platinum, gold, silver, and bronze trophies
     */
    public TrophyCountDTO getPlayerTrophyCount(UUID playerId) {
        List<TrophyCountProjection> counts = this.trophyRepository.fetchTrophyCountForPlayer(playerId);
        return this.trophyCountMapper.toDTO(counts);
    }

    /**
     * Searches and retrieves a paginated list of trophies earned by a specific player.
     *
     * @param playerId   the unique identifier of the player whose earned trophies are being queried
     * @param pageNumber the page number for the paginated results
     * @param pageSize   the number of items to include on each page of the results
     * @return a list of EarnedTrophyDTO objects representing the player's earned trophies
     */
    public SearchDTO<TrophyDTO> searchEarnedTrophiesByPlayer(UUID playerId, int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        List<TrophyProjection> projections = this.trophyRepository.searchPlayerEarnedTrophies(
                playerId,
                pageSize,
                offset
        );
        long totalEarnedTrophies = this.trophyRepository.getEarnedTrophyCountForPlayer(playerId);

        return SearchDTO.<TrophyDTO>builder()
                .content(projections.stream().map(this.trophyMapper::toTrophyDTO).toList())
                .total(totalEarnedTrophies)
                .build();
    }

    public List<TrophyDTO> fetchPlayerCollectionTrophies(UUID playerId, UUID collectionId) {
        List<TrophyProjection> projections = this.trophyRepository.fetchPlayerTrophyCollections(playerId, collectionId);
        return projections.stream().map(this.trophyMapper::toTrophyDTO).toList();
    }

    public SearchDTO<ObtainedTrophyDTO> searchLastObtained(int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        List<ObtainedTrophyDTO> trophies = this.trophyRepository.searchObtainedTrophies(pageSize, offset).stream().map(
                trophyMapper::toObtainedDTO).toList();
        long total = this.trophyRepository.getTotalEarnedTrophies();

        return SearchDTO.<ObtainedTrophyDTO>builder()
                .content(trophies)
                .total(total)
                .build();
    }

}
