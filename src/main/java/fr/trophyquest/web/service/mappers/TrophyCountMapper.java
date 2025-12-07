package fr.trophyquest.web.service.mappers;

import fr.trophyquest.web.service.dto.TrophyCountDTO;
import fr.trophyquest.web.service.entity.projections.TrophyCountProjection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TrophyCountMapper {

    public TrophyCountDTO toDTO(List<TrophyCountProjection> projections) {
        Map<String, Integer> counts = projections.stream()
                .collect(Collectors.toMap(
                        TrophyCountProjection::getTrophyType,
                        p -> Integer.parseInt(p.getTrophyCount()),
                        Integer::sum
                ));

        return new TrophyCountDTO(
                counts.getOrDefault("platinum", 0),
                counts.getOrDefault("gold", 0),
                counts.getOrDefault("silver", 0),
                counts.getOrDefault("bronze", 0)
        );
    }

}
