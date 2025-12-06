package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.PlayerDTO;
import fr.trophyquest.web.service.dto.PlayerSummaryDTO;
import fr.trophyquest.web.service.dto.TrophyCountDTO;
import fr.trophyquest.web.service.entity.projections.PlayerWithTrophyCountProjection;
import org.springframework.stereotype.Component;

@Component
public class PlayerWithTrophyCountMapper {

    public PlayerSummaryDTO toDTO(PlayerWithTrophyCountProjection projection) {
        PlayerDTO player = PlayerDTO.builder()
                .id(projection.getId())
                .pseudo(projection.getPseudo())
                .avatarUrl(projection.getAvatarUrl())
                .build();

        TrophyCountDTO trophyCountDTO = TrophyCountDTO.builder()
                .platinum(projection.getPlatinumTrophyCount())
                .gold(projection.getGoldTrophyCount())
                .silver(projection.getSilverTrophyCount())
                .bronze(projection.getBronzeTrophyCount())
                .build();

        return PlayerSummaryDTO.builder()
                .player(player)
                .trophyCount(trophyCountDTO)
                .totalGamesPlayed(projection.getTotalGamesPlayed())
                .lastPlayedCollectionId(projection.getLastCollectionId())
                .lastPlayedGameId(projection.getLastGameId())
                .lastPlayedGameTitle(projection.getLastGameTitle())
                .lastPlayedGameImageUrl(projection.getLastGameImageUrl())
                .build();
    }

}
