package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.EarnedTrophyDTO;
import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.dto.TrophyCountDTO;
import fr.trophyquest.web.service.entity.projections.EarnedTrophyProjection;
import fr.trophyquest.web.service.entity.projections.TrophyCountProjection;
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
            TrophyRepository trophyRepository, TrophyCountMapper trophyCountMapper,
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
    public SearchDTO<EarnedTrophyDTO> searchUserEarnedTrophies(UUID userId, int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        List<EarnedTrophyProjection> projections = this.trophyRepository.searchUserEarnedTrophies(
                userId,
                pageSize,
                offset
        );
        List<EarnedTrophyDTO> trophies = projections.stream().map(this.trophyMapper::toEarnedTrophyDTO).toList();
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
    public List<EarnedTrophyDTO> fetchUserCollectionTrophies(UUID userId, UUID collectionId) {
        return this.trophyRepository.fetchUserCollectionTrophies(userId, collectionId).stream()
                .map(this.trophyMapper::toEarnedTrophyDTO)
                .toList();
    }
}
