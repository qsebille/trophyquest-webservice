package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.TrophyDTO;
import fr.trophyquest.web.service.mapper.TrophyDTOMapper;
import fr.trophyquest.web.service.entity.Trophy;
import fr.trophyquest.web.service.repository.TrophyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TrophyService {

    private final TrophyRepository trophyRepository;
    private final TrophyDTOMapper trophyMapper;

    public TrophyService(TrophyRepository trophyRepository, TrophyDTOMapper trophyMapper) {
        this.trophyRepository = trophyRepository;
        this.trophyMapper = trophyMapper;
    }

    public List<TrophyDTO> findAll() {
        return trophyRepository.findAll()
                .stream()
                .map(trophyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TrophyDTO getTrophyById(UUID trophyId) {
        Trophy trophy = trophyRepository.findById(trophyId).orElseThrow();
        return trophyMapper.toDTO(trophy);
    }

    public List<TrophyDTO> getTrophyForGame(UUID gameId) {
        return trophyRepository.findByGameId(gameId)
                .stream()
                .map(trophyMapper::toDTO)
                .collect(Collectors.toList());
    }
}
