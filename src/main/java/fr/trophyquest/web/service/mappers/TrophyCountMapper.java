package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.TrophyCountByTypeDto;
import fr.trophyquest.web.service.entity.projections.TrophyCountProjection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TrophyCountMapper {

    public TrophyCountByTypeDto toDTO(List<TrophyCountProjection> projections) {
        Map<String, Integer> counts = projections.stream()
                .collect(Collectors.toMap(
                        TrophyCountProjection::getTrophyType,
                        p -> Integer.parseInt(p.getTrophyCount()),
                        Integer::sum
                ));

        int countPlatinum = counts.getOrDefault("platinum", 0);
        int countGold = counts.getOrDefault("gold", 0);
        int countSilver = counts.getOrDefault("silver", 0);
        int countBronze = counts.getOrDefault("bronze", 0);

        return TrophyCountByTypeDto.builder()
                .platinum(countPlatinum)
                .gold(countGold)
                .silver(countSilver)
                .bronze(countBronze)
                .build();
    }

}
