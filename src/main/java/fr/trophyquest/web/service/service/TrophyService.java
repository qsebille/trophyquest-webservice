package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.TrophyDTO;
import fr.trophyquest.web.service.entity.PsnTrophy;
import fr.trophyquest.web.service.entity.projections.UserTrophyProjection;
import fr.trophyquest.web.service.mapper.TrophyDTOMapper;
import fr.trophyquest.web.service.repository.PsnTrophyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TrophyService {

    private final PsnTrophyRepository psnTrophyRepository;
    private final TrophyDTOMapper trophyMapper;

    public TrophyService(
            PsnTrophyRepository psnTrophyRepository,
            TrophyDTOMapper trophyMapper
    ) {
        this.psnTrophyRepository = psnTrophyRepository;
        this.trophyMapper = trophyMapper;
    }

    public List<TrophyDTO> findAll() {
        return psnTrophyRepository.findAll().stream().map(trophyMapper::toDTO).collect(Collectors.toList());
    }

    public TrophyDTO getTrophyById(UUID trophyId) {
        PsnTrophy trophy = psnTrophyRepository.findById(trophyId).orElseThrow();
        return trophyMapper.toDTO(trophy);
    }

    public List<TrophyDTO> getGameTrophies(UUID gameId) {
        return this.psnTrophyRepository.getGameTrophies(gameId).stream()
                .map(this.trophyMapper::toDTO)
                .toList();
    }

    public List<UserTrophyProjection> getUserGameTrophies(
            UUID userId,
            UUID gameId
    ) {
        return this.psnTrophyRepository.getUserGameTrophies(
                userId,
                gameId
        );
    }

    public List<UserTrophyProjection> getUserTrophies(
            UUID userId,
            String searchFrom,
            String searchSize
    ) {
        final int pageNumber = Integer.parseInt(searchFrom);
        final int pageSize = Integer.parseInt(searchSize);
        return this.psnTrophyRepository.getUserTrophies(
                userId,
                pageNumber,
                pageSize
        );
    }

}
