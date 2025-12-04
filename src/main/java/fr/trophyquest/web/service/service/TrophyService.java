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
     * Retrieves the trophy count for a specific user in the form of a TrophyCountDTO object.
     *
     * @param userId the unique identifier of the user whose trophy count is being queried
     * @return a TrophyCountDTO containing the counts of platinum, gold, silver, and bronze trophies
     */
    public TrophyCountDTO getUserTrophyCount(UUID userId) {
        List<TrophyCountProjection> counts = this.trophyRepository.fetchTrophyCount(userId);
        return this.trophyCountMapper.toDTO(counts);
    }

    /**
     * Searches and retrieves a paginated list of trophies earned by a specific user.
     *
     * @param userId     the unique identifier of the user whose earned trophies are being queried
     * @param pageNumber the page number for the paginated results
     * @param pageSize   the number of items to include on each page of the results
     * @return a list of EarnedTrophyDTO objects representing the user's earned trophies
     */
    public SearchDTO<TrophyDTO> searchUserEarnedTrophies(UUID userId, int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        List<TrophyProjection> projections = this.trophyRepository.searchUserEarnedTrophies(userId, pageSize, offset);
        List<TrophyDTO> trophies = projections.stream().map(this.trophyMapper::toTrophyDTO).toList();
        long totalEarnedTrophies = this.trophyRepository.getTotalEarnedTrophiesForUser(userId);
        return new SearchDTO<>(trophies, totalEarnedTrophies);
    }

    /**
     * Retrieves a list of trophies earned by a specific user from a particular collection.
     *
     * @param userId       the unique identifier of the user whose trophies are being queried
     * @param collectionId the unique identifier of the trophy collection being queried
     * @return a list of EarnedTrophyDTO objects representing the trophies earned by the user in the specified collection
     */
    public List<GameGroupTrophiesDTO> fetchUserCollectionTrophies(UUID userId, UUID collectionId) {
        List<TrophyProjection> projections = this.trophyRepository.fetchUserCollectionTrophies(userId, collectionId);

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
