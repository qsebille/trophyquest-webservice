package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.GameGroupTrophiesDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
        List<TrophyDTO> trophies = projections.stream().map(this.trophyMapper::toTrophyDTO).toList();
        long totalEarnedTrophies = this.trophyRepository.getEarnedTrophyCountForPlayer(playerId);
        return new SearchDTO<>(trophies, totalEarnedTrophies);
    }

    /**
     * Retrieves a list of trophies earned by a specific player from a particular collection.
     *
     * @param playerId     the unique identifier of the player whose trophies are being queried
     * @param collectionId the unique identifier of the trophy collection being queried
     * @return a list of EarnedTrophyDTO objects representing the trophies earned by the player in the specified collection
     */
    public List<GameGroupTrophiesDTO> fetchPlayerCollectionTrophies(UUID playerId, UUID collectionId) {
        List<TrophyProjection> projections = this.trophyRepository.fetchPlayerTrophyCollections(playerId, collectionId);

        Set<String> gameGroups = projections.stream().map(TrophyProjection::getGameGroup).collect(Collectors.toSet());
        List<GameGroupTrophiesDTO> result = new ArrayList<>();
        for (String gameGroup : gameGroups) {
            List<TrophyDTO> groupTrophies = projections.stream().filter(t -> t.getGameGroup().equals(gameGroup)).map(
                    this.trophyMapper::toTrophyDTO).toList();
            result.add(new GameGroupTrophiesDTO(gameGroup, groupTrophies));
        }

        return result;
    }

    public SearchDTO<ObtainedTrophyDTO> searchLastObtained(int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        List<ObtainedTrophyDTO> trophies = this.trophyRepository.searchObtainedTrophies(pageSize, offset).stream().map(
                trophyMapper::toObtainedDTO).toList();
        long total = this.trophyRepository.getTotalEarnedTrophies();
        return new SearchDTO<>(trophies, total);
    }

}
