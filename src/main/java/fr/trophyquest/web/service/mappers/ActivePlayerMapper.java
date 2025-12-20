package fr.trophyquest.web.service.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.trophyquest.web.service.dto.MostActivePlayerResponseDTO;
import fr.trophyquest.web.service.dto.ObtainedTrophyDTO;
import fr.trophyquest.web.service.dto.PlayerDTO;
import fr.trophyquest.web.service.entity.projections.ActivePlayerProjection;
import fr.trophyquest.web.service.entity.projections.ActivePlayerTrophyProjection;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Component
public class ActivePlayerMapper {
    private final static ZoneId ZONE_ID = ZoneId.of("Europe/Paris");
    private final ObjectMapper objectMapper;

    public ActivePlayerMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private ObtainedTrophyDTO buildTrophyDTO(ActivePlayerTrophyProjection projection) {
        String iconUrl = projection.awsIconUrl();
        if (null == iconUrl) {
            iconUrl = projection.iconUrl();
        }

        return ObtainedTrophyDTO.builder()
                .id(projection.id())
                .trophyTitle(projection.title())
                .trophyDescription(projection.description())
                .trophyType(projection.trophyType())
                .trophyIconUrl(iconUrl)
                .gameTitle(projection.gameTitle())
                .obtainedDate(Instant.parse(projection.obtainedAt()).atZone(ZONE_ID))
                .build();
    }

    private List<ObtainedTrophyDTO> readTrophies(String json) {
        if (json == null || json.isBlank()) {
            return List.of();
        }
        try {
            return objectMapper.readValue(
                    json, new TypeReference<List<ActivePlayerTrophyProjection>>() {
                    }
            ).stream().map(this::buildTrophyDTO).toList();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid last trophies JSON", e);
        }
    }

    public MostActivePlayerResponseDTO toDTO(ActivePlayerProjection projection) {
        String avatarUrl = projection.getAwsAvatarUrl().orElse(projection.getAvatarUrl());

        PlayerDTO player = PlayerDTO.builder()
                .id(projection.getId())
                .pseudo(projection.getPseudo())
                .avatarUrl(avatarUrl)
                .build();

        List<ObtainedTrophyDTO> lastTrophies = readTrophies(projection.getTrophiesJson());

        return MostActivePlayerResponseDTO.builder()
                .player(player)
                .recentTrophyCount(projection.getTrophyCount())
                .lastObtainedTrophies(lastTrophies)
                .build();
    }

}
