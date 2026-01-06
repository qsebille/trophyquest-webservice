package fr.trophyquest.web.service.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.trophyquest.web.service.dto.CandidateDTO;
import fr.trophyquest.web.service.dto.GameCandidatesDTO;
import fr.trophyquest.web.service.entity.projections.CandidateProjection;
import fr.trophyquest.web.service.entity.projections.GameCandidatesProjection;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public class GameCandidatesMapper {
    private final ObjectMapper objectMapper;

    public GameCandidatesMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public GameCandidatesDTO toDTO(GameCandidatesProjection projection) {
        String imageUrl = projection.getGameAwsImageUrl().orElse(projection.getGameImageUrl());

        return GameCandidatesDTO.builder()
                .gameId(projection.getGameId())
                .gameTitle(projection.getGameTitle())
                .gameImageUrl(imageUrl)
                .candidates(this.readCandidates(projection.getCandidatesJson()))
                .build();
    }
    
    private List<CandidateDTO> readCandidates(String json) {
        if (json == null || json.isBlank()) {
            return List.of();
        }
        try {
            return objectMapper.readValue(
                    json, new TypeReference<List<CandidateProjection>>() {
                    }
            ).stream().map(this::buildCandidateDto).toList();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid last trophies JSON", e);
        }
    }

    private CandidateDTO buildCandidateDto(CandidateProjection projection) {
        return CandidateDTO.builder()
                .id(projection.id())
                .name(projection.name())
                .cover(projection.cover())
                .releaseDate(Date.valueOf(projection.releaseDate()))
                .build();
    }
}
