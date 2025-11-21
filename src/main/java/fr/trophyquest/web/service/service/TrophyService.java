package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.TrophyCountDTO;
import fr.trophyquest.web.service.entity.projections.TrophyCountProjection;
import fr.trophyquest.web.service.mappers.TrophyCountMapper;
import fr.trophyquest.web.service.repository.TrophyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrophyService {

    private final TrophyRepository trophyRepository;
    private final TrophyCountMapper trophyCountMapper;

    public TrophyService(TrophyRepository trophyRepository, TrophyCountMapper trophyCountMapper) {
        this.trophyRepository = trophyRepository;
        this.trophyCountMapper = trophyCountMapper;
    }


    public TrophyCountDTO getUserTrophyCount(UUID userId) {
        List<TrophyCountProjection> counts = this.trophyRepository.fetchTrophyCount(userId);
        return this.trophyCountMapper.toDTO(counts);
    }
}
