package fr.trophyquest.backend.domain.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Embeddable
@Data
public class PlayedTrophySetId implements java.io.Serializable {

    @Column(name = "player_id")
    private UUID playerId;

    @Column(name = "trophy_set_id")
    private UUID trophySetId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayedTrophySetId that)) return false;
        return Objects.equals(playerId, that.playerId) && Objects.equals(trophySetId, that.trophySetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, trophySetId);
    }
}