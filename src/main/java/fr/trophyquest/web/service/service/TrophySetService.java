package fr.trophyquest.web.service.service;

import fr.trophyquest.web.service.dto.SearchDTO;
import fr.trophyquest.web.service.dto.TrophySetDTO;
import fr.trophyquest.web.service.entity.Game;
import fr.trophyquest.web.service.mappers.TrophySetMapper;
import fr.trophyquest.web.service.repository.TrophySetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrophySetService {

    private final TrophySetRepository trophySetRepository;
    private final TrophySetMapper trophySetMapper;

    public TrophySetService(TrophySetRepository trophySetRepository, TrophySetMapper trophySetMapper) {
        this.trophySetRepository = trophySetRepository;
        this.trophySetMapper = trophySetMapper;
    }


    public SearchDTO<TrophySetDTO> searchGameCandidates(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<UUID> gameUuids = this.trophySetRepository.findGameIdsHavingPendingCandidate(pageRequest);
        List<Game> games = this.trophySetRepository.fetchGamesWithCandidatesByIds(gameUuids.getContent());

        List<TrophySetDTO> trophySets = games.stream().map(this.trophySetMapper::toDTO).toList();
        long total = gameUuids.getTotalElements();

        return SearchDTO.<TrophySetDTO>builder()
                .content(trophySets)
                .total(total)
                .build();
    }

}
