package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.GameCandidatesDTO;
import fr.trophyquest.web.service.mappers.GameCandidatesMapper;
import fr.trophyquest.web.service.repository.IgdbCandidateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IgdbService {

    private final IgdbCandidateRepository igdbCandidateRepository;
    private final GameCandidatesMapper gameCandidatesMapper;

    public IgdbService(IgdbCandidateRepository igdbCandidateRepository, GameCandidatesMapper gameCandidatesMapper) {
        this.igdbCandidateRepository = igdbCandidateRepository;
        this.gameCandidatesMapper = gameCandidatesMapper;
    }

    public List<GameCandidatesDTO> searchGameCandidates(int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        return this.igdbCandidateRepository.searchForGameCandidates(pageSize, offset).stream().map(
                this.gameCandidatesMapper::toDTO).toList();
    }

}
