package fr.trophyquest.backend.api.mapper;

import fr.trophyquest.backend.api.dto.candidate.IgdbCandidateDTO;
import fr.trophyquest.backend.api.dto.game.GameDTO;
import fr.trophyquest.backend.api.dto.game.GameWithIgdbCandidatesDTO;
import fr.trophyquest.backend.domain.entity.Game;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class GameMapper {
    private final IgdbCandidateMapper igdbCandidateMapper;

    public GameMapper(IgdbCandidateMapper igdbCandidateMapper) {
        this.igdbCandidateMapper = igdbCandidateMapper;
    }

    public GameDTO toDTO(Game game) {
        return GameDTO.builder()
                .id(game.getId())
                .name(game.getName())
                .build();
    }

    public GameWithIgdbCandidatesDTO toWithCandidatesDTO(Game game) {
        List<IgdbCandidateDTO> candidates = game.getIgdbCandidates().stream()
                .map(igdbCandidateMapper::toDTO)
                .sorted(Comparator.comparing(IgdbCandidateDTO::score, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        return GameWithIgdbCandidatesDTO.builder()
                .game(this.toDTO(game))
                .candidates(candidates)
                .build();
    }
}
