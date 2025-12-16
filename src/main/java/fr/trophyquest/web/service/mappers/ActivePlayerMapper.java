package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.MostActivePlayerResponseDTO;
import fr.trophyquest.web.service.dto.ObtainedTrophyDTO;
import fr.trophyquest.web.service.dto.PlayerDTO;
import fr.trophyquest.web.service.entity.projections.ActivePlayerTrophyProjection;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ActivePlayerMapper {
    private final static ZoneId ZONE_ID = ZoneId.of("Europe/Paris");

    public List<MostActivePlayerResponseDTO> toDTOList(List<ActivePlayerTrophyProjection> projections) {
        Map<UUID, List<ActivePlayerTrophyProjection>> byPlayer = projections.stream().collect(
                Collectors.groupingBy(
                        ActivePlayerTrophyProjection::getPlayerId, LinkedHashMap::new,
                        Collectors.toList()
                ));

        return byPlayer.entrySet().stream().map(entry -> {
            UUID playerId = entry.getKey();
            List<ActivePlayerTrophyProjection> playerProjections = entry.getValue();
            ActivePlayerTrophyProjection first = playerProjections.get(0);

            String avatarUrl = first.getPlayerAwsAvatarUrl().orElse(first.getPlayerAvatarUrl());
            PlayerDTO player = PlayerDTO.builder().id(playerId).pseudo(first.getPlayerPseudo()).avatarUrl(
                    avatarUrl).build();

            List<ObtainedTrophyDTO> trophies = playerProjections.stream().map(p -> {
                String trophyIconUrl = p.getTrophyAwsIconUrl().orElse(p.getTrophyIconUrl());
                return ObtainedTrophyDTO.builder().id(p.getTrophyId()).trophyTitle(p.getTrophyTitle()).trophyType(
                        p.getTrophyType()).trophyDescription(p.getTrophyDescription()).trophyIconUrl(
                        trophyIconUrl).gameTitle(p.getGameTitle()).obtainedDate(
                        p.getObtainedAt().atZone(ZONE_ID)).build();
            }).toList();

            return MostActivePlayerResponseDTO.builder().player(player).recentTrophyCount(
                    first.getTrophyCount()).lastObtainedTrophies(trophies).build();
        }).toList();
    }

}
