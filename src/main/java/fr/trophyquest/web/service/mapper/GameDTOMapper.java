package fr.trophyquest.web.service.mapper;

import fr.trophyquest.web.service.dto.GameDTO;
import fr.trophyquest.web.service.dto.GameTrophySetDTO;
import fr.trophyquest.web.service.entity.projections.GameProjection;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class GameDTOMapper {

    public List<GameDTO> buildGames(List<GameProjection> projections) {
        Map<String, GameDTO> byTitle = new LinkedHashMap<>();

        for (GameProjection p : projections) {
            String key = p.getTitleName() + "|" + p.getImageUrl();

            GameDTO game = byTitle.get(key);
            if (game == null) {
                List<GameTrophySetDTO> trophySets = new ArrayList<>();
                game = new GameDTO(p.getTitleId(), p.getTitleName(), p.getCategory(), p.getImageUrl(), trophySets);
                byTitle.put(key, game);
            }

            game.trophySets().add(new GameTrophySetDTO(p.getTrophySetId(), p.getTrophySetName(), p.getPlatform()));
        }

        return new ArrayList<>(byTitle.values());
    }

}